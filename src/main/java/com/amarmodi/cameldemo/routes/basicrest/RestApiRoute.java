package com.amarmodi.cameldemo.routes.basicrest;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RestApiRoute extends RouteBuilder {


    @Autowired
    private Environment environment;

    @Override
    public void configure() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.setStreamCaching(true);

        errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showProperties=true")
                .maximumRedeliveries(3)
                .redeliveryDelay(3000)
                .backOffMultiplier(2)
                .retryAttemptedLogLevel(LoggingLevel.ERROR));

        // some error code Http code any error message Error stack trace

        // Sets up the REST configuration for the endpoint
        restConfiguration()
                .contextPath(environment.getProperty("api.path"))
                .port(environment.getProperty("server.port"))
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api-title","Test REST Api")
                .apiProperty("api.version", "v1")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .jsonDataFormat("json-jackson")
                .bindingMode(RestBindingMode.json);

        rest("/api")
                .id("api-data")
                .consumes("application/json")
                .post("/bean")
                    .bindingMode(RestBindingMode.json)
                    .type(InputPost.class)
                    .to("direct:dbService")
                .get("/getData")
                    .consumes("application/json")
                    .to("direct:getService")
                .get("/{id}")
                    .consumes("application/json")
                    .to("direct:getInputId")
                .put("/{id}")
                    .bindingMode(RestBindingMode.json)
                    .type(InputPost.class)
                    .to("direct:putService")
                .post("/mongo/")
                    .bindingMode(RestBindingMode.json)
                    .type(InputPost.class)
                    .to("{{mongo.postRoute}}")
                .get("/mongo/getAll")
                    .consumes("application/json")
                    .to("{{getAllMongoDBRoute}}")
                .get("/mongo/{id}")
                    .consumes("application/json")
                    .to("{{mongo.getByIdRoute}}")
                .put("/mongo/{id}")
                    .bindingMode(RestBindingMode.json)
                    .type(InputPost.class)
                    .to("{{mongo.putByIdRoute}}")
                .delete("/mongo")
                    .bindingMode(RestBindingMode.json)
                    .type(InputPost.class)
                    .to("{{mongo.deleteByIdRoute}}");

    }


}
