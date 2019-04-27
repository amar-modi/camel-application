package com.amarmodi.cameldemo.routes.basicrest.mongodb;

import com.amarmodi.cameldemo.beans.MongoDBObjectToInputPosts;
import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.processors.MongoDBInputPostProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MongoDBGetByIdRoute extends RouteBuilder {

    @Autowired
    private MongoDBObjectToInputPosts mongoDBObjectToInputPosts;

    @Override
    public void configure() throws Exception {
        JacksonDataFormat inputPostDataFormat = new JacksonDataFormat(InputPost.class);

        from("{{mongo.getByIdRoute}}")
                .log("The received id is ${header.id}")
                .process(exchange -> {
                    String id = (String) exchange.getIn().getHeader("id");
//                    int id = (int) exchange.getIn().getHeader("id");
                    String jsonRequest= "{\t\"id\":" + Integer.valueOf(id) + "}";
                    exchange.getOut().setBody(jsonRequest);
                })
                .log("The body for the query is logged ${body}")
                .to("{{mongo.getByIdMongo}}")
                .log("The output from the id query was ${body}")
                .bean(mongoDBObjectToInputPosts)
                .log("The converted input posts are ${body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

    }
}
