package com.amarmodi.cameldemo.routes.basicrest;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GetIndividualRouteTest {

    @Autowired
    private CamelContext context;

    @Autowired
    private Environment environment;

    @Autowired
    protected ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;


    @Autowired
    protected CamelContext createCamelContext(){
        return context;
    }

    @Test
    public void testGetIndividualById(){
        List<InputPost> response = (List<InputPost>) producerTemplate.requestBodyAndHeader("direct:getInputId", "", "id", 3);
        assertEquals(3, response.get(0).getId());
    }

}