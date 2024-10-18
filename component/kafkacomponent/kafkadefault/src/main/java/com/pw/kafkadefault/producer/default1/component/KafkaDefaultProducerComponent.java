package com.pw.kafkadefault.producer.default1.component;

import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.kafka.producer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultProducerComponent {

    private final KafkaConfiguration kafkaProducerConfiguration;

    public KafkaDefaultProducerComponent(@Qualifier("kafkaProducerConfiguration") KafkaConfiguration kafkaConfiguration) {
        this.kafkaProducerConfiguration = kafkaConfiguration;
    }

    @Bean("kafkaDefaultProducer")
    public KafkaComponent kafkaProducerDefaultComponent() {
        KafkaComponent kafkaComponent = new KafkaComponent();
        kafkaComponent.setConfiguration(kafkaProducerConfiguration);
        return kafkaComponent;
    }

}
