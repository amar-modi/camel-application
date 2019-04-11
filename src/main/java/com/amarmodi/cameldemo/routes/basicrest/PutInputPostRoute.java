package com.amarmodi.cameldemo.routes.basicrest;

import com.amarmodi.cameldemo.processors.SuccessProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PutInputPostRoute extends RouteBuilder {

    @Autowired
    private SuccessProcessor successProcessor;

    @Override
    public void configure() throws Exception {
        // PUT service
        from("direct:putService")
                .routeId("put-service")
                .log("The input was ${body}")
                .convertBodyTo(String.class)
//                .process(buildSQLProcessor)
//                .to("{{dbNode}}")
                .process(successProcessor)
                .log("The unmarshalled record is ${body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

    }
}
