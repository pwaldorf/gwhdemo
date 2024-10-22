package com.pw.activemq.gwhdefault.consumer.default1.configuration;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.component.activemq.consumer.connection.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefautConsumerConnection {

    private final ActiveMqDefaultConsumerProperties defaultProperties;

    public ActiveMqDefautConsumerConnection(ActiveMqDefaultConsumerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Bean("activeMqDefaultConsumerConnectionFactory")
	@Primary
	public ConnectionFactory activeMqDefaultConsumerConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		try {
			log.debug("Set Consumer Connection Parms");
			connectionFactory.setBrokerURL(defaultProperties.getBrokerUrl());
			connectionFactory.setUserName(defaultProperties.getUsername());
			connectionFactory.setPassword(defaultProperties.getPassword());
			log.debug("Consumer Connection Parms Set");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connectionFactory;
	}

}
