package com.pw.jmscomponents.components;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.apache.camel.support.DefaultHeaderFilterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import jakarta.jms.ConnectionFactory;

@Configuration
@ConditionalOnProperty(value = "gtw.framework.component.jms.enabled", havingValue = "true", matchIfMissing = false)
public class JmsFrameworkComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsFrameworkComponent.class);

    @Autowired
    private ConnectionFactory jmsConnectionFactory;

    @Autowired
    private PlatformTransactionManager jmsTransactionManager;

    
    @Bean("jmsConsumerTransacted")
    public JmsComponent jmsConsumerTransacted() throws Exception {
              
        LOGGER.debug("jmsConsumerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(jmsConnectionFactory);
        jmsComponent.setTransacted(true);
        jmsComponent.setCacheLevelName("CACHE_CONSUMER");                
        jmsComponent.setTransactionManager(jmsTransactionManager);
        return jmsComponent;
    }

    @Bean("jmsProducerTransacted")
    public JmsComponent jmsProducerTransacted() throws Exception {
              
        LOGGER.debug("jmsProducerTransacted Component Creation");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(jmsConnectionFactory);
        jmsComponent.setTransacted(true);
        jmsComponent.setCacheLevelName("CACHE_NONE");
        jmsComponent.setTransactionManager(jmsTransactionManager);
        jmsComponent.setHeaderFilterStrategy(jmsHeaderFilterStrategy());
        return jmsComponent;
    }

    @Bean("jmsHeaderFilterStrategy")
    public DefaultHeaderFilterStrategy jmsHeaderFilterStrategy() {
        DefaultHeaderFilterStrategy headerFilterStrategy = new DefaultHeaderFilterStrategy();
        headerFilterStrategy.setOutFilterPattern("kafka.*");
        return headerFilterStrategy;
    }

}
