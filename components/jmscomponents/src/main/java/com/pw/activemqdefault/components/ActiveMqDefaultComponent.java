package com.pw.activemqdefault.components;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.support.DefaultHeaderFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;
import jakarta.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultComponent {

    @Autowired
    private ConnectionFactory activeMqDefaultConnectionFactory;

    @Autowired
    private PlatformTransactionManager activeMqDefaultTransactionManager;


    @Bean("activeMqDefaultConsumerTx")
    @Profile("!local")
    public JmsComponent activeMqDefaultConsumerTransacted() throws Exception {

        log.debug("jmsConsumerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(activeMqDefaultConnectionFactory);
        jmsComponent.setTransacted(true);
        jmsComponent.setCacheLevelName("CACHE_CONSUMER");
        jmsComponent.setTransactionManager(activeMqDefaultTransactionManager);
        return jmsComponent;
    }

    @Bean("activeMqDefaultConsumerTx")
    @Profile("local")
    public JmsComponent activeMqDefaultConsumerTransactedLocal() throws Exception {

        log.debug("jmsConsumerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        // jmsComponent.setConnectionFactory(activeMqDefaultConnectionFactory);
        // jmsComponent.setTransacted(true);
        // jmsComponent.setCacheLevelName("CACHE_CONSUMER");
        // jmsComponent.setTransactionManager(activeMqDefaultTransactionManager);
        return jmsComponent;
    }

    @Bean("activeMqDefaultProducerTx")
    @Profile("!local")
    public JmsComponent activeMqDefaultProducerTransacted() throws Exception {

        log.debug("jmsProducerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(activeMqDefaultConnectionFactory);
        jmsComponent.setTransacted(true);
        jmsComponent.setCacheLevelName("CACHE_NONE");
        jmsComponent.setTransactionManager(activeMqDefaultTransactionManager);
        jmsComponent.setHeaderFilterStrategy(activeMqDefaultHeaderFilterStrategy());
        return jmsComponent;
    }

    @Bean("activeMqDefaultProducerTx")
    @Profile("local")
    public JmsComponent activeMqDefaultProducerTransactedLocal() throws Exception {

        log.debug("jmsProducerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(activeMqDefaultConnectionFactory);
        jmsComponent.setTransacted(true);
        jmsComponent.setCacheLevelName("CACHE_NONE");
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
