package com.pw.activemqdefault.configurations;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.jms;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.JmsEndpointBuilderFactory.JmsEndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.JmsEndpointBuilderFactory.JmsEndpointProducerBuilder;
import org.apache.camel.component.jms.JmsMessageType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultEndpointConfiguration {

    @Bean("activeMqEndpointConsumer")
    @Profile("!local")
    public EndpointConsumerBuilder activeMqEndpointTransactedConsumerBuilder() {

        JmsEndpointConsumerBuilder activeMqEndpointConsumerBuilder =
                jms("activeMqDefaultConsumerTx", "queue:{{queue}}")
                    .jmsMessageType(JmsMessageType.Text);

        return activeMqEndpointConsumerBuilder;
    }

    @Bean("activeMqEndpointConsumer")
    @Profile("local")
    public EndpointConsumerBuilder activeMqEndpointConsumerBuilder() {

        JmsEndpointConsumerBuilder activeMqEndpointConsumerBuilder =
                jms("activeMqDefaultConsumer", "queue:{{queue}}")
                    .jmsMessageType(JmsMessageType.Text);

        return activeMqEndpointConsumerBuilder;
    }

    @Bean("activeMqEndpointProducer")
    @Profile("!local")
    public EndpointProducerBuilder activeMqEndpointTransactedProducerBuilder() {

        JmsEndpointProducerBuilder activeEndpointProducerBuilder =
                jms("activeMqDefaultProducerTx", "queue:{{queue}}")
                    .jmsMessageType(JmsMessageType.Text);

        return activeEndpointProducerBuilder;
    }

    @Bean("activeMqEndpointProducer")
    @Profile("local")
    public EndpointProducerBuilder activeMqEndpointProducerBuilder() {

        JmsEndpointProducerBuilder activeEndpointProducerBuilder =
                jms("activeMqDefaultProducer", "queue:{{queue}}")
                    .jmsMessageType(JmsMessageType.Text);

        return activeEndpointProducerBuilder;
    }
}