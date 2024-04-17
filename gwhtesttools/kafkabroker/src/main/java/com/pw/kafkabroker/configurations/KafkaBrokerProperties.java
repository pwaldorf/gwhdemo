package com.pw.kafkabroker.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gwh.local.kafka")
public class KafkaBrokerProperties {

    private String brokers = "PLAINTEXT://localhost:9092,REMOTE://192.168.6.50:9093";
    private int port = 9092;



}
