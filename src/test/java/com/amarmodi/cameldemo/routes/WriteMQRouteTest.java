package com.amarmodi.cameldemo.routes;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertEquals;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@MockEndpoints
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WriteMQRouteTest extends CamelTestSupport {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private Environment environment;

//    @EndpointInject(uri = "mock:{{processMQMessageRoute}}")
//    private MockEndpoint mockEndpoint;

//    @EndpointInject(uri = "mock:activemq:queue:dummyItemQueue1")
//    private MockEndpoint mqMockEndpoint;

//    @Test
//    public void testWriteToMQ() throws InterruptedException {
//
//        InputPost inputPost = new InputPost(2, "Fake Name Brddd");
//        String mqExpectReceived = "{\"id\":2,\"name\":\"Fake Name Brddd\"}";
//
//        mockEndpoint.expectedMessageCount(1);
//        mockEndpoint.expectedBodiesReceived(inputPost);
//
//        mqMockEndpoint.expectedMessageCount(1);
//        mqMockEndpoint.expectedBodiesReceived(mqExpectReceived);
//
//        producerTemplate.sendBody("{{writeMQRoute}}", inputPost);
//
//        mockEndpoint.assertIsSatisfied();
//        mqMockEndpoint.assertIsSatisfied();
//
//    }

    @Test
    public void testWriteToMQ_FailId(){
        InputPost inputPost = new InputPost(2, "Fake Name Brddd");
        String expectResponse = "{\"error\":\"Bad Request\",\"reason\":\"The value is not correct remove it\"}";
        String response = (String) producerTemplate.requestBodyAndHeader("{{writeMQRoute}}", inputPost, "failId", 1);
        assertEquals(expectResponse, response);
    }


    @Test
    public void testWriteToMQ_noName(){
        InputPost inputPost = new InputPost();
        inputPost.setId(7);
        String expectResponse = "There is an Error";
        String response = (String) producerTemplate.requestBody("{{writeMQRoute}}", inputPost);
        assertEquals(expectResponse, response);
    }
}