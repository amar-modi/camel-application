package com.amarmodi.cameldemo.routes.microservice;

import com.amarmodi.cameldemo.domain.Register;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.management.event.CamelContextStartedEvent;
import org.apache.camel.management.event.CamelContextStoppingEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.EventObject;

@Component
public class EventNotifier extends EventNotifierSupport {

    @Autowired
    private Environment environment;

    @Override
    public void notify(EventObject event) throws Exception {
        int port = Integer.valueOf(environment.getProperty("account.server.port"));
        String selfDomain = environment.getProperty("account.consul.self-domain");
        if (event instanceof CamelContextStartedEvent) {
            CamelContext context = ((CamelContextStartedEvent) event).getContext();
            ProducerTemplate t = context.createProducerTemplate();
            t.sendBody("direct:start", new Register("account" + port, "account", selfDomain, port));
        }
        // TODO Figure out why this is closing the app during startup
//        if (event instanceof CamelContextStoppingEvent) {
//            CamelContext context = ((CamelContextStoppingEvent) event).getContext();
//            ProducerTemplate t = context.createProducerTemplate();
//            t.sendBodyAndHeader("direct:stop", null, "id", "account" + port);
//        }
    }

    @Override
    public boolean isEnabled(EventObject event) {
        return (event instanceof CamelContextStartedEvent || event instanceof CamelContextStoppingEvent);
    }

}