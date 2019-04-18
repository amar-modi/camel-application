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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GetAllInputPostsRouteTest {


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
    public void testGetAllPosts(){

        InputPost inputPost = new InputPost();
        inputPost.setId(3);
        String expect = "The request was processed and created";
        inputPost.setName("Fake Person 123");
        String responsePost= (String) producerTemplate.requestBody("direct:dbService", inputPost);
        assertNotNull(responsePost);
        assertEquals(expect,responsePost);

        List<InputPost> response= (List<InputPost>) producerTemplate.requestBody("direct:getService", "");
       assertNotNull(response);
    }

}