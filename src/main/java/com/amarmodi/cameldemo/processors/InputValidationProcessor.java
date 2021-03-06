package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.exceptions.InvalidInputException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class InputValidationProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        // Meant to validate the input
        InputPost body = (InputPost) exchange.getIn().getBody();
        System.out.println("This is in the validation process");
        if(body.getName() == null){
            exchange.getOut().setBody("There is an Error");
            throw new InvalidInputException("The name is invalid so this is a failure");
        }
    }
}
