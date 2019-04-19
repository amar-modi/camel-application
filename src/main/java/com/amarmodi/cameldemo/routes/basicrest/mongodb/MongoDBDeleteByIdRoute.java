package com.amarmodi.cameldemo.routes.basicrest.mongodb;

import com.amarmodi.cameldemo.processors.InputPostToMongoDBBasicObjectProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoDBDeleteByIdRoute extends RouteBuilder {

    @Autowired
    private InputPostToMongoDBBasicObjectProcessor inputPostToMongoDBBasicObjectProcessor;
    @Override
    public void configure() throws Exception {
        from("{{mongo.deleteByIdRoute}}")
                .log("The incoming deletion params are: ${body}")
                .process(inputPostToMongoDBBasicObjectProcessor)
                .to("{{mongo.deleteByIdMongo}}")
                .log("The output from the delete operation was ${body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(202));
    }
}
