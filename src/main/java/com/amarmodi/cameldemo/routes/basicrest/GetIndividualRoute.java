package com.amarmodi.cameldemo.routes.basicrest;

import com.amarmodi.cameldemo.processors.RowProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetIndividualRoute extends RouteBuilder {

    @Autowired
    private RowProcessor rowProcessor;

    @Override
    public void configure() throws Exception {
        // GET using a param
        from("direct:getInputId")
                .to("{{selectNode}}")
                .process(rowProcessor)
                .setHeader(Exchange.HTTP_RESPONSE_TEXT, constant(200));

    }
}
