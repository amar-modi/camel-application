package com.amarmodi.cameldemo.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestClientRouteTest extends CamelTestSupport {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext(){
        return context;
    }

    @Autowired
    private Environment environment;

    @EndpointInject(uri = "mock:catchRestCountries")
    private MockEndpoint mockEndpoint;

    @EndpointInject(uri ="mock:{{restEndpointRoute}}")
    private MockEndpoint httpMockEndpoint;

    @Before
    public void mockEndpoints() throws Exception {
        AdviceWithRouteBuilder mockHttp = new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                // mock the for testing
                interceptSendToEndpoint("{{restEndpointRoute}}")
                        .skipSendToOriginalEndpoint()
                        .to("mock:catchRestCountries");
            }
        };
        context.getRouteDefinition("rest-client-route")
                .adviceWith(context,mockHttp);
    }

    @Test
    public void testEndpointCall() throws InterruptedException {
        mockEndpoint.expectedMessageCount(1);
        httpMockEndpoint.expectedMessageCount(0);
        Object o = producerTemplate.requestBody("{{restClient}}", "");
        mockEndpoint.assertIsSatisfied();
        httpMockEndpoint.assertIsSatisfied();
    }
}