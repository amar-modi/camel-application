package com.amarmodi.cameldemo.routes;

import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.exceptions.InvalidHeaderException;
import com.amarmodi.cameldemo.exceptions.InvalidInputException;
import com.amarmodi.cameldemo.processors.InputPostsProcessor;
import com.amarmodi.cameldemo.processors.InputValidationProcessor;
import com.amarmodi.cameldemo.routes.errorhandling.PrepareErrorResponse;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WriteMQRoute extends RouteBuilder {

    @Autowired
    private InputValidationProcessor inputValidationProcessor;

    @Autowired
    private InputPostsProcessor inputPostsProcessor;


    @Override
    public void configure() throws Exception {

        JacksonDataFormat postFormat = new JacksonDataFormat(InputPost.class);


        onException(InvalidHeaderException.class)
                .handled(true)
                .bean(PrepareErrorResponse.class)
                .log("Error response processed");

        from("{{writeMQRoute}}")
                .routeId("mq-write-route")
                .log("The body received on writeMQRoute is ${body}")
                .process(exchange -> {
                    Object failId = exchange.getIn().getHeader("failId");
                    if(failId != null){
                        exchange.getOut().setBody("The value is not correct remove it");
                        throw new InvalidHeaderException("The value is not correct remove it");
                    }
                })
                .process(inputPostsProcessor)
                .choice()
                    .when(exchange -> {
                        Object header = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE);
                        if(header == null) return true;
                        String headerString =  header.toString();
                        return !(header != null && headerString.equals("501"));
                    })
                        .log("The output is now going to mq ${body}")
                        .to("{{processMQMessageRoute}}")
                .otherwise()
                    .log("The body for error is ${body}")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .endChoice();



        from("{{processMQMessageRoute}}")
                .routeId("process-mq-route")
                .marshal(postFormat)
                .log("The body being sent to MQ queue is: ${body}")
                .to("log:?level=INFO&showBody=true")
                    .to(ExchangePattern.InOnly,"activemq:queue:dummyItemQueue1")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                .setBody(constant("The request was processed and created"))
                .end();

    }
}
