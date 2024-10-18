package com.pw.kafkadefault.consumer.default1.routeconfiguration;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaSaveKafkaOffset extends RouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {

        routeConfiguration("kafkaError")
            .interceptFrom()
            .setHeader("GWHOriginalMessageID").simple("${headerAs('kafka.OFFSET', String)}")
            .end();
    }
}
