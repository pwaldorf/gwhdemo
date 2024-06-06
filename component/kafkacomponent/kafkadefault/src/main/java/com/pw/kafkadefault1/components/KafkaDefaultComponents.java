package com.pw.kafkadefault1.components;

import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.default.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultComponents {

    @Autowired
    @Qualifier("kafkaDefaultConsumerConfiguration")
    KafkaConfiguration kafkaDefaultConsumerConfiguration;

    @Autowired
    @Qualifier("kafkaDefaultProducerConfiguration")
    KafkaConfiguration kafkaDefaultProducerConfiguration;

    @Bean("kafkaDefaultConsumer")
    public KafkaComponent kafkaDefaultConsumerComponent() {
        KafkaComponent kafkaComponent = new KafkaComponent();
        kafkaComponent.setConfiguration(kafkaDefaultConsumerConfiguration);
        return kafkaComponent;
    }

    @Bean("kafkaDefaultProducer")
    public KafkaComponent kafkaProducerComponentComponent() {
        KafkaComponent kafkaComponent = new KafkaComponent();
        kafkaComponent.setConfiguration(kafkaDefaultProducerConfiguration);
        return kafkaComponent;
    }

}
