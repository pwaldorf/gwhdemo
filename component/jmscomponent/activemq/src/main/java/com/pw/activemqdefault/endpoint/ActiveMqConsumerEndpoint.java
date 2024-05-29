package com.pw.activemqdefault.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.jms;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.JmsEndpointBuilderFactory.JmsEndpointConsumerBuilder;
import org.apache.camel.component.jms.JmsMessageType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.pw.activemqdefault.configurations.ActiveMqConsumerEndpointBuilder;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.activemq.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqConsumerEndpoint implements ActiveMqConsumerEndpointBuilder {

    @Override
    public EndpointConsumerBuilder getConsumerEndpoint() {
        JmsEndpointConsumerBuilder activeMqEndpointConsumerBuilder =
                jms("activeMqDefaultConsumerTx", "queue:{{queue}}")
                    .jmsMessageType(JmsMessageType.Text);

        return activeMqEndpointConsumerBuilder;
    }

}
