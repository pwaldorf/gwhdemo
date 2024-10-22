package com.pw.activemq.gwhdefault.consumer.default1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.jms;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.JmsEndpointBuilderFactory.JmsEndpointConsumerBuilder;
import org.apache.camel.component.jms.JmsMessageType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.pw.support1.route.GwhEndpointConsumerBuilder;

@Configuration
@ConditionalOnProperty(value = "gwh.component.activemq.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMQTransactionalConsumerEndpoint implements GwhEndpointConsumerBuilder {

    @Override
    public EndpointConsumerBuilder getConsumerEndpoint() {
        JmsEndpointConsumerBuilder activeMqEndpointConsumerBuilder =
                jms("activeMqDefaultConsumerTx", "queue:{{queue}}")
                    .jmsMessageType(JmsMessageType.Text);

        return activeMqEndpointConsumerBuilder;
    }

}
