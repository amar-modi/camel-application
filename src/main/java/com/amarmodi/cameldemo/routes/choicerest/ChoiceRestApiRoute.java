package com.amarmodi.cameldemo.routes.choicerest;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ChoiceRestApiRoute extends RouteBuilder {

    @Autowired
    private Environment environment;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .contextPath(environment.getProperty("api.path"))
                .port(environment.getProperty("server.port"))
                .enableCORS(true)
                .apiContextPath("/choice-doc")
                .apiProperty("choice-title","Test REST Api")
                .apiProperty("choice.version", "v1")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .jsonDataFormat("json-jackson")
                .bindingMode(RestBindingMode.json);

        rest("/choice")
                .id("choice-data")
                .consumes("application/json")
                .get("/{prefix}")
                    .consumes("appplication/json")
                    .to("direct:getChoice");

    }
}
