package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import static org.apache.camel.builder.Builder.constant;

@Component
public class InputPostsProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        InputPost body = (InputPost) exchange.getIn().getBody();
        if(body.getName() == null){
            exchange.getOut().setBody("There is an Error");
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(501));
            exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json");
        }
    }
}
