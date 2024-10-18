package com.pw.kafkadefault.producer.default1.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.kafka.producer.default1")
public class KafkaDefaultProducerProperties {

    private String brokers;
    private String defaultEndpoint = "kafkaDefaultProducerEndpoint";
    private String transactionalEndpoint = "kafkaTransactionalProducerEndpoint";

    private String defaultRoutePolicy = "kafkaProducer";
    private String defaultRouteConfigurationPattern = ".*kafkaProducerError.*,.*kafkaProducerConfig.*";
}
