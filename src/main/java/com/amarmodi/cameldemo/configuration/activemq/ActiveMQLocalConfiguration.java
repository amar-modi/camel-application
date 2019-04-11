package com.amarmodi.cameldemo.configuration.activemq;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.jms.ConnectionFactory;

@Configuration
@Profile("test")
public class ActiveMQLocalConfiguration {

    @Bean(name="activemq")
    public ActiveMQComponent createComponent(ConnectionFactory connectionFactory){
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConnectionFactory(connectionFactory);
        return activeMQComponent;
    }
}
