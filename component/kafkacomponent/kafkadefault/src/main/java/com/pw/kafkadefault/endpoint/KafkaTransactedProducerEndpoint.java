package com.pw.kafkadefault.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.KafkaEndpointProducerBuilder;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.configurations.KafkaProducerEndpointBuilder;

@Component
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
