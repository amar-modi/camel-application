package com.amarmodi.cameldemo.routes.basicrest;

import com.amarmodi.cameldemo.processors.RowProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAllInputPostsRoute extends RouteBuilder {

    @Autowired
    private RowProcessor rowProcessor;

    @Override
    public void configure() throws Exception {
        // GET
        from("direct:getService")
                .routeId("direct-getService")
                .log("Invoking the getService and retrieving all data from database")
                .to("sql:select * from input_posts?dataSource=dataSource")
                .log("---> The body is : ${body}")
                .process(rowProcessor)
                .setHeader(Exchange.HTTP_RESPONSE_TEXT, constant(200));

    }
}
