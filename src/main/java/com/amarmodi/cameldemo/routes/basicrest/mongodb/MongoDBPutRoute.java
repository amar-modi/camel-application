package com.amarmodi.cameldemo.routes.basicrest.mongodb;

import com.amarmodi.cameldemo.processors.MongoDBPutProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoDBPutRoute extends RouteBuilder {


    @Autowired
    private MongoDBPutProcessor mongoDBPutProcessor;

    @Override
    public void configure() throws Exception {
        from("{{mongo.putByIdRoute}}")
                .routeId("mongo-put-by-id-route")
                .log("The received put object is: ${body}")
                .process(mongoDBPutProcessor)
                .to("{{mongo.putByIdMongo}}")
                .log("The returned body was ${body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

    }
}
