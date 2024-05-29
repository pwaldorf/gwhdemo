package com.pw.kafkadefault.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.AdvancedKafkaEndpointConsumerBuilder;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.configurations.KafkaConsumerEndpointBuilder;

@Component
public class KafkaDefaultConsumerEndpoint implements KafkaConsumerEndpointBuilder {

    @Override
    public EndpointConsumerBuilder getConsumerEndpoint() {
        AdvancedKafkaEndpointConsumerBuilder kafkaEndpointConsumerBuilder =
                kafka("kafkaDefaultConsumer", "{{topic}}")
                        .groupId("{{groupId}}")
                        .allowManualCommit("{{allowManualCommit}}")
                        .autoCommitEnable("{{autoCommitEnable}}")
                        .advanced()
                        .isolationLevel("{{isolationLevel}}");

        return kafkaEndpointConsumerBuilder;
    }

}
