package com.amarmodi.cameldemo.routes;

import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.processors.*;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class RestApiRoute extends RouteBuilder {


    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private BuildSQLProcessor buildSQLProcessor;

    @Autowired
    private SuccessProcessor successProcessor;

    @Autowired
    private InputValidationProcessor inputValidationProcessor;

    @Autowired
    private RowProcessor rowProcessor;

    @Override
    public void configure() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.setStreamCaching(true);

        errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showProperties=true")
                .maximumRedeliveries(3)
                .redeliveryDelay(3000)
                .backOffMultiplier(2)
                .retryAttemptedLogLevel(LoggingLevel.ERROR));

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
                    .to("direct:getInputId");

        // POST
        from("direct:dbService")
                .routeId("direct-route")
                .tracing()
                .process(inputValidationProcessor)
                .log(">>> ${body.id}")
                .log(">>> ${body.name}")
                .process(buildSQLProcessor)
                .to("jdbc:dataSource")
                .end()
                .process(successProcessor)
                .to("direct:responseService");

        from("direct:responseService")
                .routeId("direct-response")
                .tracing()
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

        // GET
        from("direct:getService")
                .routeId("direct-getService")
                .log("Invoking the getService and retrieving all data from database")
                .to("sql:select * from input_posts?dataSource=dataSource")
                .log("---> The body is : ${body}")
                .process(rowProcessor)
                .setHeader(Exchange.HTTP_RESPONSE_TEXT, constant(200));

        // GET using a param
        from("direct:getInputId")
                .setBody(simple("select * from country where country_code = '${header.id}'"))
                .to("jdbc:dataSource")
                .setHeader(Exchange.HTTP_RESPONSE_TEXT, constant(200));
    }


}
