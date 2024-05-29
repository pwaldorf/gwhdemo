package com.pw.kafkadefault.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.KafkaEndpointProducerBuilder;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.configurations.KafkaProducerEndpointBuilder;

@Component
public class KafkaDefaultProducerEndpoint implements KafkaProducerEndpointBuilder {

    @Override
    public EndpointProducerBuilder getProducerEndpoint() {
        KafkaEndpointProducerBuilder kafkaEndpointProducerBuilder =
                kafka("kafkaDefaultProducer", "{{topic}}")
                        .bufferMemorySize("{{bufferMemorySize}}")
                        .lingerMs("{{lingerMs}}");

        return kafkaEndpointProducerBuilder;
    }

}
