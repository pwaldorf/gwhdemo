package com.pw.activemqdefault1.configurations;

import javax.jms.ConnectionFactory;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default1.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefautConnection {

    private final ActiveMqDefaultConsumerProperties defaultProperties;

    public ActiveMqDefautConnection(ActiveMqDefaultConsumerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Bean("activeMqDefaultConnectionFactory")
	@ConditionalOnProperty(value = "spring.artemis.mode", havingValue = "native", matchIfMissing = true)
	public ConnectionFactory activeMqDefaultConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		try {
			log.debug("Set Connection Parms");
			connectionFactory.setBrokerURL(defaultProperties.getBrokerUrl());
			connectionFactory.setUser(defaultProperties.getUsername());
			connectionFactory.setPassword(defaultProperties.getPassword());
			log.debug("Connection Parms Set");
		} catch (Exception e) {
			e.printStackTrace();
		}

		WrapCachingConnectionFactory wrapConnectionFactory = new WrapCachingConnectionFactory(connectionFactory);
		wrapConnectionFactory.setSessionCacheSize(defaultProperties.getSessionCacheSize());

		return wrapConnectionFactory;
	}

}
