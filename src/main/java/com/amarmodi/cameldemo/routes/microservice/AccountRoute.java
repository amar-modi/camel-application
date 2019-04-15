package com.amarmodi.cameldemo.routes.microservice;

import com.amarmodi.cameldemo.domain.Account;
import org.apache.camel.Exchange;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AccountRoute extends RouteBuilder {

    @Autowired
    private Environment environment;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("netty-http")
                .bindingMode(RestBindingMode.json)
                .port(environment.getProperty("account.server.port"));

        // Register the service with Consul
        from("direct:start").marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to(environment.getProperty("account.consul.start"));
        from("direct:stop").shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                .toD(environment.getProperty("account.consul.stop") + "/${header.id}");

        rest("/account")
                .get("/customerxyz/{customerId}")
                    .to("bean:accountService?method=findByCustomerId(${header.customerId})")
                .get("/getAll/{id}")
                    .to("bean:accountService?method=findAll")
                .post("/create/{post}").consumes("application/json").type(Account.class)
                    .to("bean:accountService?method=add(${body})")
                .get("/{id}")
                    .to("bean:accountService?method=findById(${header.id})");
    }
}
