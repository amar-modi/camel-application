package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.InputPost;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class MongoDBInputPostProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        BasicDBObject body = (BasicDBObject) exchange.getIn().getBody();
        InputPost inputPost = new InputPost();
        inputPost.setName((String)body.get("name"));
        inputPost.setId((int)body.get("id"));
        exchange.getOut().setBody(inputPost);
        System.out.println("The body is Converted over to : " + body);
    }
}
