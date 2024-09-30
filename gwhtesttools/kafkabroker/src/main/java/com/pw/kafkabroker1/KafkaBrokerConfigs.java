package com.pw.kafkabroker1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@Configuration
@Profile("local")
@ConditionalOnProperty(value = "gwh.framework.devtool.kafka.broker.enabled", havingValue = "true")
public class KafkaBrokerConfigs {

    KafkaBrokerProperties kafkaBrokerProperties;

    public KafkaBrokerConfigs(KafkaBrokerProperties kafkaBrokerProperties) {
        this.kafkaBrokerProperties = kafkaBrokerProperties;
    }

    @Bean
    public EmbeddedKafkaBroker broker(KafkaBrokerProperties kafkaBrokerProperties) {
        Map<String, String> properties = new HashMap<>();
        properties.put("auto.create.topics.enable", "true");
        properties.put("transaction.state.log.replication.factor", "1");
        properties.put("transaction.state.log.min.isr", "1");
        properties.put("listeners", kafkaBrokerProperties.getBrokers());
        properties.put("advertised.listeners", kafkaBrokerProperties.getBrokers());
        properties.put("listener.security.protocol.map", "PLAINTEXT:PLAINTEXT,REMOTE:PLAINTEXT");
        return new EmbeddedKafkaBroker(1)
            .kafkaPorts(kafkaBrokerProperties.getPort())
            .brokerProperties(properties)
            .brokerListProperty("spring.kafka.bootstrap-servers");

    }

}
