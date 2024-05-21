package com.pw.activemqdefault.configurations;


import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default.enabled", havingValue = "true", matchIfMissing = false)
@Profile("dev")
public class ActiveMqDefaultConfigurations {

    private final ActiveMqDefaultProperties activeMqDefaultProperties;

    public ActiveMqDefaultConfigurations(ActiveMqDefaultProperties activeMqDefaultProperties) {
        this.activeMqDefaultProperties = activeMqDefaultProperties;
    }

    @Bean("activeMqDefaultConnectionFactory")
	public ConnectionFactory activeMqDefaultConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		try {
			log.debug("Set Connection Parms");
			connectionFactory.setBrokerURL(activeMqDefaultProperties.getBroker());
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

    @Bean("activeMqDefaultTransactionManager")
    public PlatformTransactionManager activeMqDefaultTransactionManager() {
        JmsTransactionManager transactionManager = new JmsTransactionManager(activeMqDefaultConnectionFactory());
        return transactionManager;
    }

    @Bean("txRequiredActiveMqDefault")
    public SpringTransactionPolicy txRequiredActiveMqDefault() {
        SpringTransactionPolicy required = new SpringTransactionPolicy();
        required.setTransactionManager(activeMqDefaultTransactionManager());
        required.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        return required;
    }

    @Bean("txRequiredNewActiveMqDefault")
    public SpringTransactionPolicy txRequiredNewActiveMqDefault() {
        SpringTransactionPolicy required = new SpringTransactionPolicy();
        required.setTransactionManager(activeMqDefaultTransactionManager());
        required.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
        return required;
    }

    @Bean("txRequiredMandatoryActiveMqDefault")
    public SpringTransactionPolicy txRequiredMandatoryActiveMqDefault() {
        SpringTransactionPolicy required = new SpringTransactionPolicy();
        required.setTransactionManager(activeMqDefaultTransactionManager());
        required.setPropagationBehaviorName("PROPAGATION_REQUIRES_Mandatory");
        return required;
    }
}
