package com.amarmodi.cameldemo.routes.basicrest.mongodb;

import com.amarmodi.cameldemo.beans.MongoDBObjectToInputPosts;
import com.amarmodi.cameldemo.processors.MongoDBInputPostProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MongoDBRoute extends RouteBuilder {

    @Autowired
    private Environment environment;

    @Autowired
    private MongoDBInputPostProcessor mongoDBInputPostProcessor;

    @Autowired
    private MongoDBObjectToInputPosts mongoDBObjectToInputPosts;

    @Override
    public void configure() throws Exception {
        from("{{getAllMongoDBRoute}}")
                .log("In the MongoDBRoute with body of ${body}")
                .to("{{getAllRequest}}")
                .log("The mongoDB return object is ${body}")
                .split(body())
                    .process(mongoDBInputPostProcessor)
                    .log("The body is out of splitPRoce ${body}")
                .end()
                .log("The body out of split is ${body}")
                .to("{{mongoDbBeanConversionRoute}}");

        from("{{mongoDbBeanConversionRoute}}")
                .bean(mongoDBObjectToInputPosts)
                .log("The bean proccessor return ${body}")
                .setHeader(Exchange.HTTP_RESPONSE_TEXT, constant(200))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

    }
}
