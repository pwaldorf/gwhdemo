package com.pw.kafkadefault.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.kafka.default.consumer")
public class KafkaDefaultConsumerProperties {

    /**
     * Kafka broker url
     */
    String brokers;

}