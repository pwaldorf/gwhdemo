package com.pw.kafkadefault.configurations;

import org.apache.camel.component.kafka.KafkaConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultConfigurations {

    KafkaDefaultProperties kafkaDefaultProperties;

    public KafkaDefaultConfigurations(KafkaDefaultProperties kafkaDefaultProperties) {
        this.kafkaDefaultProperties = kafkaDefaultProperties;
    }

    @Bean("kafkaDefaultConsumerConfiguration")
    public KafkaConfiguration kafkaDefaultConsumerConfiguration() {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        kafkaConfiguration.setBrokers(kafkaDefaultProperties.getConsumerBrokers());
        return kafkaConfiguration;
    }

    @Bean("kafkaDefaultProducerConfiguration")
    public KafkaConfiguration kafkaDefaultProducerConfiguration() {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        kafkaConfiguration.setBrokers(kafkaDefaultProperties.getProducerBrokers());
        return kafkaConfiguration;
    }
}
