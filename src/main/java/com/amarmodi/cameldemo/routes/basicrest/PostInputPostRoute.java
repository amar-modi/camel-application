package com.amarmodi.cameldemo.routes.basicrest;

import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.processors.BuildSQLProcessor;
import com.amarmodi.cameldemo.processors.InputValidationProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
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

        JacksonDataFormat postFormat = new JacksonDataFormat(InputPost.class);

        // TODO NEsted route 1/2/3/ more routes consume throw an exception on last route
        // should be sending back 500 error code and message
        // send body as json
        // DELETE/ PUT
        // MongoDB
        // push the json to MongoDB insert delete find findAll
        // Any error send 500 and error message
        // Jackson Not Gson
        // Week after next
        // POST
        from("direct:dbService")
                .routeId("direct-route")
                .tracing()
                .log(">>> ${body.id}")
                .log(">>> ${body.name}")
                .log("The converted body is ${body}")
                .to("{{loggingPostRoute}}");

    }
}
