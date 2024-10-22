package com.pw.activemq.gwhdefault.consumer.default1.component;

import javax.jms.ConnectionFactory;

import org.apache.camel.component.jms.JmsComponent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.component.activemq.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqConsumerComponent {

    private ConnectionFactory connectionFactory;

    private PlatformTransactionManager activeMqDefaultTransactionManager;

    public ActiveMqConsumerComponent(ConnectionFactory connectionFactory, PlatformTransactionManager activeMqDefaultTransactionManager) {
        this.connectionFactory = connectionFactory;
        this.activeMqDefaultTransactionManager = activeMqDefaultTransactionManager;
    }

    @Bean("activeMqDefaultConsumer")
    public JmsComponent activeMqDefaultConsumer() throws Exception {

        log.info("jmsConsumer Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);
        jmsComponent.setTransacted(false);
        jmsComponent.setCacheLevelName("CACHE_NONE");
        return jmsComponent;
    }


    @Bean("activeMqDefaultConsumerTx")
    public JmsComponent activeMqDefaultConsumerTransacted() throws Exception {

        log.info("jmsConsumerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);
        jmsComponent.setTransacted(true);
        jmsComponent.setCacheLevelName("CACHE_CONSUMER");
        jmsComponent.setTransactionManager(activeMqDefaultTransactionManager);
        return jmsComponent;
    }
}
