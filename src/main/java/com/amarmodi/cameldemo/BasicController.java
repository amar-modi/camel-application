package com.amarmodi.cameldemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @RequestMapping(method = RequestMethod.GET, value = {"/",""})
    public String hello(){
        return "This app works";
    }
}
