package com.amarmodi.cameldemo.routes;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class WriteMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("{{writeMQRoute}}")
                .routeId("mq-write-route")
                .log("The body is 333333333 ${body}")
                .to("log:?level=INFO&showBody=true")
                .loadBalance().random()
                    .to(ExchangePattern.InOnly,"activemq:queue:dummyItemQueue1")
                    .to(ExchangePattern.InOnly,"activemq:queue:dummyItemQueue2")
                .end();
    }
}
