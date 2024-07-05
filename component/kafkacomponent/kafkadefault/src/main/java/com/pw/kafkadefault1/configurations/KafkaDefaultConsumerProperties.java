package com.pw.kafkadefault1.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.kafka.default1.consumer")
public class KafkaDefaultConsumerProperties {


    private String brokers;
    private String autoCommitEndpoint = "kafkaDefaultConsumerEndpoint";
    private String manualCommitEndpoint = "kafkaDefaultConsumerEndpoint";

    private String defaultRoutePolicy = "kafkaConsumer";
    private String defaultRouteConfigurationPattern = ".*kafkaConsumerError.*,.*kafkaConsumerConfig.*";

}