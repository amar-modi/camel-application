package com.amarmodi.cameldemo.processors;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class GetParamSQLProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM INPUT_POSTS");
        exchange.getOut().setBody(query.toString());
    }
}
