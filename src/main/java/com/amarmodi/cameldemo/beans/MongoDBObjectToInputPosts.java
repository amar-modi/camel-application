package com.amarmodi.cameldemo.beans;

import com.amarmodi.cameldemo.domain.InputPost;
import com.mongodb.BasicDBObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongoDBObjectToInputPosts {

    public List<InputPost> convert(List<BasicDBObject> list){
        List<InputPost> output = new ArrayList<>();
        for(BasicDBObject body: list){
            InputPost inputPost = new InputPost();
            inputPost.setName((String)body.get("name"));
            inputPost.setId((int)body.get("id"));
            output.add(inputPost);
            System.out.println("The body is Converted over to : " + body);
        }
        System.out.println("The output is the following: " + output);
        return output;
    }
}
