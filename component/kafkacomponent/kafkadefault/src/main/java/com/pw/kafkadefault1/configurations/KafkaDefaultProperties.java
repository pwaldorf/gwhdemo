package com.pw.kafkadefault1.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.kafka.default1")
public class KafkaDefaultProperties {

    /**
     * Kafka consumer broker url
     */
    private String consumerBrokers;
    private String autoCommitConsumerEndpoint = "kafkaDefaultConsumerEndpoint";
    private String manualCommitConsumerEndpoint = "kafkaDefaultConsumerEndpoint";

    /**
     * Kafka producer broker url
     */
    private String producerBrokers;
    private String defaultProducerEndpoint = "kafkaDefaultProducerEndpoint";
    private String transactionalProducerEndpoint = "kafkaTransactedProducerEndpoint";

}