package com.amarmodi.cameldemo.routes.basicrest;

import com.amarmodi.cameldemo.processors.InputValidationProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostInputPostRoute extends RouteBuilder {

    @Autowired
    private InputValidationProcessor inputValidationProcessor;

    @Override
    public void configure() throws Exception {
        // POST
        from("direct:dbService")
                .routeId("direct-route")
                .tracing()
                .process(inputValidationProcessor)
                .log(">>> ${body.id}")
                .log(">>> ${body.name}")
                .convertBodyTo(String.class)
                .log("The converted body is ${body}")
                .to("{{writeMQRoute}}");

        from("activemq:queue:dummyItemQueue")
                .routeId("direct-response")
                .log("Messsage read from AMQ Queue : ${body}")
                .transform(simple("The queue works successfully ${body}"))
                .convertBodyTo(String.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

    }
}
