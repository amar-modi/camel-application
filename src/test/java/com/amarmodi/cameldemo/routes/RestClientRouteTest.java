package com.amarmodi.cameldemo.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.*;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@MockEndpoints
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestClientRouteTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext(){
        return context;
    }

    @Autowired
    private Environment environment;

    @EndpointInject(uri = "mock:https://restcountries.eu/rest/v2/alpha/us")
    private MockEndpoint mockEndpoint;

    @Test
    public void testEndpointCall() throws InterruptedException {
        mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "GET");
        mockEndpoint.expectedHeaderReceived(Exchange.HTTP_URI, "https://restcountries.eu/rest/v2/alpha/us");
        DefaultExchange defaultExchange = new DefaultExchange(context);
        producerTemplate.send("{{restClient}}", defaultExchange);
        mockEndpoint.assertIsSatisfied();
    }
}