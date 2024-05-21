package com.pw.kafkadefault.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.kafka.default")
public class KafkaDefaultProperties {

    /**
     * Kafka consumer broker url
     */
    String consumerBrokers;

    /**
     * Kafka producer broker url
     */
    String producerBrokers;

}