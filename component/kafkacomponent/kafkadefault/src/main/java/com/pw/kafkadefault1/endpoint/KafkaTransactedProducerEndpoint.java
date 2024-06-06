package com.pw.kafkadefault1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.KafkaEndpointProducerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault1.configurations.KafkaProducerEndpointBuilder;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaTransactedProducerEndpoint implements KafkaProducerEndpointBuilder {

    @Override
    public EndpointProducerBuilder getProducerEndpoint() {
        KafkaEndpointProducerBuilder kafkaEndpointProducerBuilder =
                kafka("kafkaDefaultProducer", "{{topic}}")
                        .additionalProperties("transactional.id", "{{transactionalId}}")
                        .additionalProperties("enable.idempotence", "{{idempotence}}")
                        .additionalProperties("retries", "{{retries}}")
                        .additionalProperties("max.in.flight.requests.per.connection", "{{maxInflightRequests}}")
                        .bufferMemorySize("{{bufferMemorySize}}")
                        .lingerMs("{{lingerMs}}");

        return kafkaEndpointProducerBuilder;
    }

}
