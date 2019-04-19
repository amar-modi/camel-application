package com.amarmodi.cameldemo.routes.basicrest.mongodb;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.*;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MongoDBDeleteByIdRouteTest {

    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext(){
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Test
    public void testDeleteByIdMongoDB(){
        InputPost inputPost = new InputPost();
        inputPost.setName("Jon Snow");
        int id = 2002;
        inputPost.setId(id);
        InputPost responsePost = (InputPost) producerTemplate.requestBody("{{mongo.postRoute}}", inputPost);
        assertEquals(inputPost.getId(), responsePost.getId());
        assertEquals(inputPost.getName(), responsePost.getName());

        Object o = producerTemplate.requestBody("{{mongo.deleteByIdRoute}}", inputPost);
        assertNotNull(o);
    }
}