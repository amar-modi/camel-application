package com.amarmodi.cameldemo.routes.microservice;

import javax.annotation.PreDestroy;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@Service
@DependsOn("camelContext")
public class Unregistration {

	@Value("${server.port}")
	private int port;
	
	@Autowired
	ProducerTemplate template;

	@PreDestroy
	public void hello() {
		template.sendBodyAndHeader("direct:stop", null, "id", "account" + "8083");
	}
	
}