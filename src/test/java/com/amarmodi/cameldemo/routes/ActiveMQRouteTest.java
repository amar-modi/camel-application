package com.amarmodi.cameldemo.routes;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ActiveMQRouteTest extends CamelTestSupport{

    @Autowired
    private CamelContext context;

    @Autowired
    private Environment environment;

    @Autowired
    protected CamelContext createCamelContext(){
        return context;
    }

    @Autowired
    protected ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    protected RouteBuilder createRouteBuilder(){
        return new ActiveMQRoute();
    }

    @Before
    public void setup(){

    }

    @Test
    public void testActiveMQRead(){
        // TODO going to need an embedded ActiveMQ
        String input = "{\"id\":\"4\", \"name\":\"Tom\"}";
        ArrayList<InputPost> response =(ArrayList<InputPost>) producerTemplate.requestBody("activemq:inputItemQueue", input);
        System.out.println(response);
        assertNotNull(response);

    }

    @Test(expected = CamelExecutionException.class)
    public void testActiveMQRead_Fail(){
        String input = "{\"id\":\"4\"}";
        ArrayList<InputPost> response =(ArrayList<InputPost>) producerTemplate.requestBody("activemq:inputItemQueue", input);
        System.out.println(response);
        assertNotNull(response);

    }

}