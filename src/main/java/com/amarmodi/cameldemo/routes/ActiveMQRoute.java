package com.amarmodi.cameldemo.routes;

import com.amarmodi.cameldemo.domain.InputPost;
import com.amarmodi.cameldemo.exceptions.InvalidMessageException;
import com.amarmodi.cameldemo.processors.BuildSQLProcessor;
import com.amarmodi.cameldemo.processors.ValidateActiveMQMessageProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ActiveMQRoute extends RouteBuilder {

    @Autowired
    private ValidateActiveMQMessageProcessor validateActiveMQMessageProcessor;

    @Autowired
    private BuildSQLProcessor buildSQLProcessor;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure() throws Exception {

        onException(InvalidMessageException.class).log(LoggingLevel.ERROR, "Invalid Message was sent ${body}")
                .to("{{errorQueue}}");

        JacksonDataFormat inputPostDataFormat = new JacksonDataFormat(InputPost.class);

        from("activemq:inputItemQueue")
                .log("Read MEsssage From active MQ: ${body}")
                .unmarshal(inputPostDataFormat)
                .log("Unmarshalled object is : ${body}")
                .process(validateActiveMQMessageProcessor)
                .process(buildSQLProcessor)
                .to("{{toRoute}}")
                .to("{{selectNode}}")
                .log("The output from the select query was: ${body}");
    }
}
