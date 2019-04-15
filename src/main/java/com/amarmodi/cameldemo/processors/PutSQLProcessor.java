package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class PutSQLProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        InputPost input  = (InputPost) exchange.getIn().getBody();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE INPUT_POSTS SET name = '" + input.getName() + "' WHERE id = " + input.getId() +";" );
        exchange.getIn().setBody(query.toString());
    }
}
