package com.pw.activemq.gwhdefault.producer.default1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.jms;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.JmsEndpointBuilderFactory.JmsEndpointProducerBuilder;
import org.apache.camel.component.jms.JmsMessageType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.pw.support1.route.GwhEndpointProducerBuilder;

@Configuration
@ConditionalOnProperty(value = "gwh.component.activemq.producer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqProducerEndpoint implements GwhEndpointProducerBuilder {

    @Override
    public EndpointProducerBuilder getProducerEndpoint() {
        JmsEndpointProducerBuilder activeEndpointProducerBuilder =
                jms("activeMqDefaultProducerTx", "queue:{{queue}}")
                    .jmsMessageType(JmsMessageType.Text);

        return activeEndpointProducerBuilder;
    }

}