package com.amarmodi.cameldemo.routes.basicrest;

import com.amarmodi.cameldemo.domain.Country;
import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.processors.BuildSQLProcessor;
import com.amarmodi.cameldemo.processors.InputValidationProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostInputPostRoute extends RouteBuilder {

    @Autowired
    private InputValidationProcessor inputValidationProcessor;

    @Autowired
    private BuildSQLProcessor buildSQLProcessor;

    @Override
    public void configure() throws Exception {

        GsonDataFormat postFormat = new GsonDataFormat(InputPost.class);


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

        from("activemq:queue:dummyItemQueue1","activemq:queue:dummyItemQueue2")
                .routeId("direct-response")
                .log("Messsage read from AMQ Queue : ${body}")
//                .transform(simple("The queue works successfully ${body}"))
                .convertBodyTo(String.class)
                .unmarshal(postFormat)
                .process(buildSQLProcessor)
                .to("{{dbNode}}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

    }
}
