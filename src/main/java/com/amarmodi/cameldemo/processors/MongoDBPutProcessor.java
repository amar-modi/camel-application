package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.InputPost;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class MongoDBPutProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        InputPost post = (InputPost) exchange.getIn().getBody();
        int id = (int) exchange.getIn().getHeader("id");
        DBObject filterObject = new BasicDBObject("id", id);
        DBObject updateObject = new BasicDBObject("$set", new BasicDBObject("name", post.getName()));
        exchange.getOut().setBody((new Object[]{filterObject, updateObject}));
    }
}
