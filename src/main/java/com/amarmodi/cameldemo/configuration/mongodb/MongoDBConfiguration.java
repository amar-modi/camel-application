package com.amarmodi.cameldemo.configuration.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBConfiguration {


    @Autowired
    private MongoDBConnectionProperties mongoDBConnectionProperties;

    @Bean(name="mongodb")
    public MongoClient mongoClient(){
        String username = mongoDBConnectionProperties.getUsername();
        String password = mongoDBConnectionProperties.getPassword();
        String authenticationDatabase = mongoDBConnectionProperties.getAuthenticationDatabase();
        String database = mongoDBConnectionProperties.getDatabase();
        String host = mongoDBConnectionProperties.getHost();
        String port = mongoDBConnectionProperties.getPort();
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://"+username+":"+password+"@"+host+"/"+authenticationDatabase);
//        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://openshiftmongo:openshiftpass@mongodb/sampledb");
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        return mongoClient;
    }

//    Username: openshiftmongo
//    Password: openshiftpass
//    Database Name: sampledb
//    Connection URL: mongodb://openshiftmongo:openshiftpass@mongodb/sampledb


}
