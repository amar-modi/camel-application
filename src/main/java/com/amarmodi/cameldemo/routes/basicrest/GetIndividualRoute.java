package com.amarmodi.cameldemo.routes.basicrest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class GetIndividualRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // GET using a param
        from("direct:getInputId")
                .setBody(simple("select * from input_posts where input_posts.id = '${header.id}'"))
                .to("jdbc:dataSource")
                .setHeader(Exchange.HTTP_RESPONSE_TEXT, constant(200));

    }
}
