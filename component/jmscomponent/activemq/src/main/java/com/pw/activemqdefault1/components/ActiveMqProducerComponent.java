package com.pw.activemqdefault1.components;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.support.DefaultHeaderFilterStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.transaction.PlatformTransactionManager;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqProducerComponent {

    private ConnectionFactory connectionFactory;

    private PlatformTransactionManager activeMqDefaultTransactionManager;

    public ActiveMqProducerComponent(ConnectionFactory connectionFactory, PlatformTransactionManager activeMqDefaultTransactionManager) {
        this.connectionFactory = connectionFactory;
        this.activeMqDefaultTransactionManager = activeMqDefaultTransactionManager;
    }

    @Bean("activeMqDefaultProducer")
    public JmsComponent activeMqDefaultProducer() throws Exception {

        log.info("jmsProducer Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);
        jmsComponent.setCacheLevelName("CACHE_PRODUCER");
        jmsComponent.setHeaderFilterStrategy(activeMqDefaultHeaderFilterStrategy());
        return jmsComponent;
    }

    @Bean("activeMqDefaultProducerTx")
    public JmsComponent activeMqDefaultProducerTransacted() throws Exception {

        log.info("jmsProducerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);
        jmsComponent.setTransacted(true);
        jmsComponent.setCacheLevelName("CACHE_PRODUCER");
        jmsComponent.setTransactionManager(activeMqDefaultTransactionManager);
        jmsComponent.setHeaderFilterStrategy(activeMqDefaultHeaderFilterStrategy());
        return jmsComponent;
    }

    @Bean("activeMqDefaultHeaderFilterStrategy")
    public DefaultHeaderFilterStrategy activeMqDefaultHeaderFilterStrategy() {
        DefaultHeaderFilterStrategy headerFilterStrategy = new DefaultHeaderFilterStrategy();
        headerFilterStrategy.setOutFilterPattern("kafka.*");
        return headerFilterStrategy;
    }

}
