package com.pw.kafkabroker1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gwh.local.kafka")
public class KafkaBrokerProperties {

    private String brokers = "PLAINTEXT://localhost:9092,REMOTE://localhost:9093";
    private int port = 9092;



}
