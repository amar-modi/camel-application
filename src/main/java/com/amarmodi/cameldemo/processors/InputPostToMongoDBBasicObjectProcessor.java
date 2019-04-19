package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.InputPost;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class InputPostToMongoDBBasicObjectProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        InputPost inputPost = (InputPost) exchange.getIn().getBody();
        BasicDBObject query = new BasicDBObject();
        if(inputPost.getId() > 0){
            query.append("id", inputPost.getId());
        }
        if(inputPost.getName() != null){
            query.append("name", inputPost.getName());
        }
        exchange.getOut().setBody(query);
    }
}
