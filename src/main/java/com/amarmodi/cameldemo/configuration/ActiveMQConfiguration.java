package com.amarmodi.cameldemo.configuration;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
public class ActiveMQConfiguration {

    @Bean(name="activemq")
    public ActiveMQComponent createComponent(ConnectionFactory connectionFactory){
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConnectionFactory(connectionFactory);
        return activeMQComponent;
    }


}
