package com.pw.kafkadefault.configurations;

import org.apache.camel.component.kafka.KafkaConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultConfigurations {

    KafkaDefaultConsumerProperties kafkaDefaultConsumerProperties;
    KafkaDefaultProducerProperties kafkaDefaultProducerProperties;

    public KafkaDefaultConfigurations(KafkaDefaultConsumerProperties kafkaDefaultConsumerProperties, KafkaDefaultProducerProperties kafkaDefaultProducerProperties) {
        this.kafkaDefaultConsumerProperties = kafkaDefaultConsumerProperties;
        this.kafkaDefaultProducerProperties = kafkaDefaultProducerProperties;
    }

    @Bean("kafkaDefaultConsumerConfiguration")
    public KafkaConfiguration kafkaDefaultConsumerConfiguration() {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        kafkaConfiguration.setBrokers(kafkaDefaultConsumerProperties.getBrokers());
        return kafkaConfiguration;
    }

    @Bean("kafkaDefaultProducerConfiguration")
    public KafkaConfiguration kafkaDefaultProducerConfiguration() {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        kafkaConfiguration.setBrokers(kafkaDefaultConsumerProperties.getBrokers());
        return kafkaConfiguration;
    }
}
