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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MongoDBPutRouteTest {

    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext(){
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Test
    public void testPutInputPostMongoDB(){
        InputPost inputPost = new InputPost();
        inputPost.setName("Ernie");
        int id = 2001;
        inputPost.setId(id);
        InputPost responsePost = (InputPost) producerTemplate.requestBody("{{mongo.postRoute}}", inputPost);
        assertEquals(inputPost.getId(), responsePost.getId());
        assertEquals(inputPost.getName(), responsePost.getName());

        InputPost putInputPost = new InputPost();
        putInputPost.setId(id);
        putInputPost.setName("Burt");
        Object putResponse = producerTemplate.requestBodyAndHeader("{{mongo.putByIdRoute}}", putInputPost,"id", id );

        List<InputPost> response = (List<InputPost>) producerTemplate.requestBodyAndHeader("{{mongo.getByIdRoute}}", "", "id", id);
        assertNotNull(response);
        assertEquals(id, response.get(0).getId());
        assertEquals(putInputPost.getName(), response.get(0).getName());
    }

}