package com.pw.kafkadefault.consumer.default1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.AdvancedKafkaEndpointConsumerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.support1.route.GwhEndpointConsumerBuilder;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultConsumerEndpoint implements GwhEndpointConsumerBuilder {

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
