package com.amarmodi.cameldemo.routes.basicrest.mongodb;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class MongoDBPostRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        JacksonDataFormat inputPostDataFormat = new JacksonDataFormat(InputPost.class);

        from("{{mongo.postRoute}}")
                .log("The body for the  insert is : ${body}")
                .to("{{mongo.insertInputPost}}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

    }
}
