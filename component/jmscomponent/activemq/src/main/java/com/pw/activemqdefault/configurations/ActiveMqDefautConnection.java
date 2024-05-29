package com.pw.activemqdefault.configurations;

import javax.jms.ConnectionFactory;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefautConnection {

    private final ActiveMqDefaultProperties activeMqDefaultProperties;

    public ActiveMqDefautConnection(ActiveMqDefaultProperties activeMqDefaultProperties) {
        this.activeMqDefaultProperties = activeMqDefaultProperties;
    }

    @Bean("activeMqDefaultConnectionFactory")
	@ConditionalOnProperty(value = "spring.artemis.mode", havingValue = "native", matchIfMissing = true)
	public ConnectionFactory activeMqDefaultConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		try {
			log.debug("Set Connection Parms");
			connectionFactory.setBrokerURL(activeMqDefaultProperties.getBrokerUrl());
			connectionFactory.setUser(activeMqDefaultProperties.getUsername());
			connectionFactory.setPassword(activeMqDefaultProperties.getPassword());
			log.debug("Connection Parms Set");
		} catch (Exception e) {
			e.printStackTrace();
		}

		WrapCachingConnectionFactory wrapConnectionFactory = new WrapCachingConnectionFactory(connectionFactory);
		wrapConnectionFactory.setSessionCacheSize(activeMqDefaultProperties.getSessionCacheSize());

		return wrapConnectionFactory;
	}

}
