package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.exceptions.InvalidMessageException;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class ValidateActiveMQMessageProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        InputPost body = (InputPost) exchange.getIn().getBody();
        if(body.getName() == null){
            throw new InvalidMessageException("The name is invalid so this is a failure");
        }
    }
}
