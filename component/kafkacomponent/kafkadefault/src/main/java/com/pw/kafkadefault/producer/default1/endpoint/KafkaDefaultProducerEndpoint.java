package com.pw.kafkadefault.producer.default1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.KafkaEndpointProducerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.support1.route.GwhEndpointProducerBuilder;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.producer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultProducerEndpoint implements GwhEndpointProducerBuilder {

    @Override
    public EndpointProducerBuilder getProducerEndpoint() {
        KafkaEndpointProducerBuilder kafkaEndpointProducerBuilder =
                kafka("kafkaDefaultProducer", "{{topic}}")
                        .bufferMemorySize("{{bufferMemorySize}}")
                        .lingerMs("{{lingerMs}}")
                        .producerBatchSize("{{producerBatchSize}}")
                        .compressionCodec("{{compressionCodec}}");

        return kafkaEndpointProducerBuilder;
    }

}