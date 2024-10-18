package com.pw.kafkadefault.producer.default1.configuration;

import org.apache.camel.component.kafka.KafkaConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.kafka.producer.default1.enabled", havingValue = "true", matchIfMissing = false)
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
