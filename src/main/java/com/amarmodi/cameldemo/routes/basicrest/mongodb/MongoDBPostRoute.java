package com.amarmodi.cameldemo.routes.basicrest.mongodb;

import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.processors.MongoDBInputPostProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoDBPostRoute extends RouteBuilder {

    @Autowired
    private MongoDBInputPostProcessor mongoDBInputPostProcessor;

    @Override
    public void configure() throws Exception {

        JacksonDataFormat inputPostDataFormat = new JacksonDataFormat(InputPost.class);

        from("{{mongo.postRoute}}")
                .log("The body for the  insert is : ${body}")
                .to("{{mongo.insertInputPost}}")
                .process(mongoDBInputPostProcessor)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

    }
}
