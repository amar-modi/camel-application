package com.amarmodi.cameldemo.routes.basicrest.mongodb;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MongoDBGetByIdRouteTest {

    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext(){
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Test
    public void testGetByIdMongoDBRoute() {
        InputPost inputPost = new InputPost();
        inputPost.setName("Ernie");
        int id = 510;
        inputPost.setId(id);
        InputPost responsePost = (InputPost) producerTemplate.requestBody("{{mongo.postRoute}}", inputPost);
        assertEquals(inputPost.getId(), responsePost.getId());
        assertEquals(inputPost.getName(), responsePost.getName());

        List<InputPost> response = (List<InputPost>) producerTemplate.requestBodyAndHeader("{{mongo.getByIdRoute}}", "", "id", id);
        assertNotNull(response);
        assertEquals(id, response.get(0).getId());
    }
}