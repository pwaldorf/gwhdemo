package com.pw.activemqdefault1.configurations;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.jms;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.JmsEndpointBuilderFactory.JmsEndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.JmsEndpointBuilderFactory.JmsEndpointProducerBuilder;
import org.apache.camel.component.jms.JmsMessageType;

// @Configuration
// @ConditionalOnProperty(value = "gwh.framework.component.activemq.default1.default.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultEndpointConfiguration {

    // @Bean("activeMqEndpointConsumer")
    public EndpointConsumerBuilder activeMqEndpointTransactedConsumerBuilder() {

        JmsEndpointConsumerBuilder activeMqEndpointConsumerBuilder =
                jms("activeMqDefaultConsumerTx", "queue:{{queue}}")
                    .jmsMessageType(JmsMessageType.Text);

        return activeMqEndpointConsumerBuilder;
    }

    // @Bean("activeMqEndpointProducer")
    public EndpointProducerBuilder activeMqEndpointTransactedProducerBuilder() {

        JmsEndpointProducerBuilder activeEndpointProducerBuilder =
                jms("activeMqDefaultProducerTx", "queue:{{queue}}")
                    .jmsMessageType(JmsMessageType.Text);

        return activeEndpointProducerBuilder;
    }

}
