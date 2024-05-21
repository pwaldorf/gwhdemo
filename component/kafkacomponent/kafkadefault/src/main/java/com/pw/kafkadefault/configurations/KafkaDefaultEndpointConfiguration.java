package com.pw.kafkadefault.configurations;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.AdvancedKafkaEndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory.KafkaEndpointProducerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pw.kafkadefault.components.KafkaDefaultComponents;

@Configuration
@ConditionalOnBean(KafkaDefaultComponents.class)
public class KafkaDefaultEndpointConfiguration {

    @Bean("kafkaEndpointManualCommitConsumer")
    public EndpointConsumerBuilder kafkaEndpointManualCommitConsumerBuilder() {

        AdvancedKafkaEndpointConsumerBuilder kafkaEndpointConsumerBuilder =
                kafka("kafkaDefaultConsumer", "{{topic}}")
                        .groupId("{{groupId}}")
                        .allowManualCommit(true)
                        .autoCommitEnable(false)
                        .advanced()
                        .isolationLevel("{{isolationLevel}}");

        return kafkaEndpointConsumerBuilder;
    }

    @Bean("kafkaEndpointAutoCommitConsumer")
    public EndpointConsumerBuilder kafkaEndpointAutoCommitConsumerBuilder() {

        AdvancedKafkaEndpointConsumerBuilder kafkaEndpointConsumerBuilder =
                kafka("kafkaDefaultConsumer", "{{topic}}")
                        .groupId("{{groupId}}")
                        .allowManualCommit(false)
                        .autoCommitEnable(true)
                        .advanced()
                        .isolationLevel("{{isolationLevel}}");

        return kafkaEndpointConsumerBuilder;
    }

    @Bean("kafkaEndpointDefaultProducer")
    public EndpointProducerBuilder kafkaEndpointDefaultBuilder() {

        KafkaEndpointProducerBuilder kafkaEndpointProducerBuilder =
                kafka("kafkaDefaultProducer", "{{topic}}")
                        .bufferMemorySize("{{bufferMemorySize}}")
                        .lingerMs("{{lingerMs}}");

        return kafkaEndpointProducerBuilder;
    }

    @Bean("kafkaEndpointTransactedProducer")
    public EndpointProducerBuilder kafkaEndpointTransactedBuilder() {

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
