package com.pw.jmscomponents.components.config;


import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import jakarta.jms.ConnectionFactory;


@Configuration
@ConditionalOnProperty(value = "gtw.framework.component.jms.enabled", havingValue = "true", matchIfMissing = false)
public class JmsConfigs {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsConfigs.class);
    
    @Bean("jmsConnectionFactory")
	public ConnectionFactory jmsConnectionFactory() {		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		
		try {
			LOGGER.info("Set Connection Parms");
			connectionFactory.setBrokerURL("tcp://192.168.6.1:61616");
			connectionFactory.setUser("artemis");
			connectionFactory.setPassword("artemis");			
			LOGGER.info("Connection Parms Set");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		WrapCachingConnectionFactory wrapConnectionFactory = new WrapCachingConnectionFactory(connectionFactory);
		wrapConnectionFactory.setSessionCacheSize(10);
		
		return wrapConnectionFactory;
	}    

    @Bean("jmsTransactionManager")
    public PlatformTransactionManager jmsTransactionManager() {
        JmsTransactionManager transactionManager = new JmsTransactionManager(jmsConnectionFactory());
        return transactionManager;
    }

    @Bean("txRequired")
    public SpringTransactionPolicy txRequired() {
        SpringTransactionPolicy required = new SpringTransactionPolicy();
        required.setTransactionManager(jmsTransactionManager());
        required.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        return required;    
    }

    @Bean("txRequiredNew")
    public SpringTransactionPolicy txRequiredNew() {
        SpringTransactionPolicy required = new SpringTransactionPolicy();
        required.setTransactionManager(jmsTransactionManager());
        required.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
        return required;    
    }

    @Bean("txRequiredMandatory")
    public SpringTransactionPolicy txRequiredMandatory() {
        SpringTransactionPolicy required = new SpringTransactionPolicy();
        required.setTransactionManager(jmsTransactionManager());
        required.setPropagationBehaviorName("PROPAGATION_REQUIRES_Mandatory");
        return required;    
    }
}
