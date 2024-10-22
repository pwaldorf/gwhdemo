package com.pw.activemq.gwhdefault.configurations.default1;

import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.jms.ConnectionFactory;


@Configuration
@ConditionalOnProperty(value = "gwh.component.activemq.configuration.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultConfiguration {

    private final ConnectionFactory connectionFactory;

    public ActiveMqDefaultConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean("activeMqDefaultTransactionManager")
    public PlatformTransactionManager activeMqDefaultTransactionManager() {
        JmsTransactionManager transactionManager = new JmsTransactionManager(connectionFactory);
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
