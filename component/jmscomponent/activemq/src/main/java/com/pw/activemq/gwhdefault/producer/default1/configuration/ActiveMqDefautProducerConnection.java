package com.pw.activemq.gwhdefault.producer.default1.configuration;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pw.activemq.gwhdefault.configurations.default1.WrapCachingConnectionFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.component.activemq.producer.connection.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefautProducerConnection {

    private final ActiveMqDefaultProducerProperties defaultProperties;

    public ActiveMqDefautProducerConnection(ActiveMqDefaultProducerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Bean("activeMqDefaultProducerConnectionFactory")
	public ConnectionFactory activeMqDefaultProducerConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		try {
			log.debug("Set Producer Connection Parms");
			connectionFactory.setBrokerURL(defaultProperties.getBrokerUrl());
			connectionFactory.setUserName(defaultProperties.getUsername());
			connectionFactory.setPassword(defaultProperties.getPassword());
			log.debug("Producer Connection Parms Set");
		} catch (Exception e) {
			e.printStackTrace();
		}

		WrapCachingConnectionFactory wrapConnectionFactory = new WrapCachingConnectionFactory(connectionFactory);
		wrapConnectionFactory.setSessionCacheSize(defaultProperties.getSessionCacheSize());
		return wrapConnectionFactory;
	}

}
