package com.pw.kafkadefault.consumer.default1.component;

import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.kafka.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultConsumerComponent {

    @Autowired
    @Qualifier("kafkaConsumerConfiguration")
    KafkaConfiguration kafkaConsumerConfiguration;

    @Bean("kafkaDefaultConsumer")
    public KafkaComponent kafkaDefaultConsumerComponent() {
        KafkaComponent kafkaComponent = new KafkaComponent();
        kafkaComponent.setConfiguration(kafkaConsumerConfiguration);
        return kafkaComponent;
    }
}
