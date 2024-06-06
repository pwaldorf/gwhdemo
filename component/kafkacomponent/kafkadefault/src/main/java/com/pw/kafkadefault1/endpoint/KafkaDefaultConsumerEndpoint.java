package com.pw.kafkadefault1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.AdvancedKafkaEndpointConsumerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault1.configurations.KafkaConsumerEndpointBuilder;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.consumer.enabled", havingValue = "true", matchIfMissing = false)
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
