package com.amarmodi.cameldemo.routes.errorhandling;

import com.amarmodi.cameldemo.exceptions.InvalidHeaderException;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.json.simple.JsonObject;
import org.springframework.http.MediaType;

public class PrepareErrorResponse {

    @Handler
    public void prepareErrorResponse(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);

        if (cause instanceof InvalidHeaderException) {
            InvalidHeaderException validationEx = (InvalidHeaderException) cause;
        }

        Message msg = exchange.getOut();
        msg.setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        msg.setHeader(Exchange.HTTP_RESPONSE_CODE, 500);

        JsonObject errorMessage = new JsonObject();
        errorMessage.put("error", "Bad Request");
        errorMessage.put("reason", cause.getMessage());
        msg.setBody(errorMessage.toString());
        // we need to do the fault=false below in order to prevent a
        // HTTP 500 error code from being returned
//        msg.setFault(false);
    }
}
