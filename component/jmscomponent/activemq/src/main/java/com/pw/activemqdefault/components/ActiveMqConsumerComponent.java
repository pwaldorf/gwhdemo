package com.pw.activemqdefault.components;

import javax.jms.ConnectionFactory;

import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.activemq.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqConsumerComponent {

    @Autowired
    private ConnectionFactory activeMqDefaultConnectionFactory;

    @Autowired
    private PlatformTransactionManager activeMqDefaultTransactionManager;

    @Bean("activeMqDefaultConsumer")
    public JmsComponent activeMqDefaultConsumer() throws Exception {

        log.debug("jmsConsumer Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(activeMqDefaultConnectionFactory);
        jmsComponent.setTransacted(false);
        jmsComponent.setCacheLevelName("CACHE_NONE");
        return jmsComponent;
    }


    @Bean("activeMqDefaultConsumerTx")
    public JmsComponent activeMqDefaultConsumerTransacted() throws Exception {

        log.debug("jmsConsumerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(activeMqDefaultConnectionFactory);
        jmsComponent.setTransacted(true);
        jmsComponent.setCacheLevelName("CACHE_CONSUMER");
        jmsComponent.setTransactionManager(activeMqDefaultTransactionManager);
        return jmsComponent;
    }
}
