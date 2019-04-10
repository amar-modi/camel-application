package com.amarmodi.cameldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamelDemoApplication.class, args);
        System.out.println("HELLO APP STARTED");
    }

}
