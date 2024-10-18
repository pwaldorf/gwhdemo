package com.pw.kafkadefault.consumer.default1.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.kafka.consumer.default1")
public class KafkaDefaultConsumerProperties {


    private String brokers;
    private String autoCommitEndpoint = "kafkaDefaultConsumerEndpoint";
    private String manualCommitEndpoint = "kafkaDefaultConsumerEndpoint";

    private String defaultRoutePolicy = "kafkaConsumer";
    private String defaultRouteConfigurationPattern = "kafkaConsumerManualCommit"; //".*kafkaConsumerError.*,.*kafkaConsumerConfig.*";

}