package com.pw.kafkadefault.producer.default1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.KafkaEndpointProducerBuilder;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.support1.route.GwhEndpointProducerBuilder;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.producer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaTransactionalProducerEndpoint implements GwhEndpointProducerBuilder {

    @Override
    public EndpointProducerBuilder getProducerEndpoint() {
        KafkaEndpointProducerBuilder kafkaEndpointProducerBuilder =
                kafka("kafkaDefaultProducer", "{{topic}}")
                        .additionalProperties("transactional.id", "{{transactionalId}}")
                        .additionalProperties("enable.idempotence", "{{idempotence}}")
                        .additionalProperties(ProducerConfig.RETRIES_CONFIG, "{{retries}}")
                        .additionalProperties(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "{{maxInflightRequests}}")
                        .additionalProperties(ProducerConfig.MAX_BLOCK_MS_CONFIG, "{{maxBlocksMs}}")
                        .schemaRegistryURL("{{schemaRegistryUrl}}")
                        .bufferMemorySize("{{bufferMemorySize}}")
                        .lingerMs("{{lingerMs}}");

        return kafkaEndpointProducerBuilder;
    }

}
