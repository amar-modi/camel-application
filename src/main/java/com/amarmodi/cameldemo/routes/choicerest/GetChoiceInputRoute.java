package com.amarmodi.cameldemo.routes.choicerest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class GetChoiceInputRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:getChoice")
                .routeId("choice-route")
                .choice()
                    .when(header("prefix").endsWith("first"))
                        .to("direct:firstRoute")
                    .when(header("prefix").endsWith("second"))
                        .to("direct:secondRoute")
                    .otherwise()
                        .to("direct:defaultRoute");

        from("direct:firstRoute")
                .transform(simple("The ${header.prefix} is first"));

        from("direct:secondRoute")
                .transform(simple("The ${header.prefix} is second"));

        from("direct:defaultRoute")
                .transform(simple("The ${header.prefix} is default"));

    }
}
