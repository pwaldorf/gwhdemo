package com.pw.kafkadefault1.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.kafka.default1.producer")
public class KafkaDefaultProducerProperties {

    private String brokers;
    private String defaultEndpoint = "kafkaDefaultProducerEndpoint";
    private String transactionalEndpoint = "kafkaTransactionalProducerEndpoint";

    private String defaultRoutePolicy = "kafkaProducer";
    private String defaultRouteConfigurationPattern = ".*kafkaProducerError.*,.*kafkaProducerConfig.*";
}
