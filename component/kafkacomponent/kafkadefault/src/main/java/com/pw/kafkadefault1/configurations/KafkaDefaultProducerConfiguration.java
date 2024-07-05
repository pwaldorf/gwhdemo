package com.pw.kafkadefault1.configurations;

import org.apache.camel.component.kafka.KafkaConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultProducerConfiguration {

    private final KafkaDefaultProducerProperties kafkaDefaultProducerProperties;

    public KafkaDefaultProducerConfiguration(KafkaDefaultProducerProperties kafkaDefaultProducerProperties) {
        this.kafkaDefaultProducerProperties = kafkaDefaultProducerProperties;
    }

    @Bean("kafkaProducerConfiguration")
    public KafkaConfiguration kafkaProducerConfiguration() {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        kafkaConfiguration.setBrokers(kafkaDefaultProducerProperties.getBrokers());
        return kafkaConfiguration;
    }
}
