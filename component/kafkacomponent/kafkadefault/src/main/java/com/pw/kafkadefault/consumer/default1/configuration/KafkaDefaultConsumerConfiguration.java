package com.pw.kafkadefault.consumer.default1.configuration;

import org.apache.camel.component.kafka.KafkaConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.kafka.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultConsumerConfiguration {

    private final KafkaDefaultConsumerProperties kafkaDefaultConsumerProperties;

    public KafkaDefaultConsumerConfiguration(KafkaDefaultConsumerProperties kafkaDefaultConsumerProperties) {
        this.kafkaDefaultConsumerProperties = kafkaDefaultConsumerProperties;
    }

    @Bean("kafkaConsumerConfiguration")
    public KafkaConfiguration kafkaConsumerConfiguration() {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        kafkaConfiguration.setBrokers(kafkaDefaultConsumerProperties.getBrokers());
        return kafkaConfiguration;
    }

}
