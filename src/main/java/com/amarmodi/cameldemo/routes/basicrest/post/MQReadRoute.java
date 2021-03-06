package com.amarmodi.cameldemo.routes.basicrest.post;

import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.processors.BuildSQLProcessor;
import com.amarmodi.cameldemo.processors.InputValidationProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQReadRoute extends RouteBuilder {

    @Autowired
    private InputValidationProcessor inputValidationProcessor;

    @Autowired
    private BuildSQLProcessor buildSQLProcessor;

    @Override
    public void configure() throws Exception {
        JacksonDataFormat postFormat = new JacksonDataFormat(InputPost.class);

        from("activemq:queue:dummyItemQueue1")
                .to("{{mqDummyReadRoute}}");

        from("{{mqDummyReadRoute}}")
                .routeId("direct-response")
                .log("Messsage read from AMQ Queue : ${body}")
                .unmarshal(postFormat)
                .process(inputValidationProcessor)
                .process(buildSQLProcessor)
                .to("{{dbNode}}")
                .setBody(constant("The application successfully wrote into the db"));
    }
}
