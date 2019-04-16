package com.amarmodi.cameldemo.routes.basicrest.post;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class LoggingPostRoute extends RouteBuilder {

    @Autowired
    private Environment environment;

    @Override
    public void configure() throws Exception {
        from("{{loggingPostRoute}}")
                .routeId("loggingPostRoute")
                .log("The received Body in the logging route is ${body}")
        .to("{{secondLevelPostRoute}}");

        from("{{secondLevelPostRoute}}")
                .routeId("secondLevelPostRoute")
                .log("The received Body in the logging route is ${body}")
        .to("{{writeMQRoute}}");
    }
}
