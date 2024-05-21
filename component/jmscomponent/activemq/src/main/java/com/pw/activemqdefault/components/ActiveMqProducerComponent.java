package com.pw.activemqdefault.components;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.support.DefaultHeaderFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.activemq.producer.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqProducerComponent {

    @Autowired
    private ConnectionFactory activeMqDefaultConnectionFactory;

    @Autowired
    private PlatformTransactionManager activeMqDefaultTransactionManager;

    @Bean("activeMqDefaultProducer")
    public JmsComponent activeMqDefaultProducer() throws Exception {

        log.debug("jmsProducer Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setCacheLevelName("CACHE_PRODUCER");
        jmsComponent.setHeaderFilterStrategy(activeMqDefaultHeaderFilterStrategy());
        return jmsComponent;
    }

    @Bean("activeMqDefaultProducerTx")
    public JmsComponent activeMqDefaultProducerTransacted() throws Exception {

        log.debug("jmsProducerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(activeMqDefaultConnectionFactory);
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