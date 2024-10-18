package com.pw.kafkadefault.consumer.default1.endpoint;

import org.apache.camel.builder.endpoint.EndpointRouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pw.kafkadefault.consumer.default1.bean.KafkaDefaultConsumerManualCommit;

// @Configuration
// @ConditionalOnProperty(value = "gwh.framework.component.kafka.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaManualCommitRouteConfigEndpoint {

    // @Bean("kafkaConsumerManualCommit")
    public EndpointRouteConfigurationBuilder kafkaConsumerManualCommit() {
        return new EndpointRouteConfigurationBuilder() {

            @Override
            public void configuration() throws Exception {
                routeConfiguration("kafkaConsumerManualCommit")
                    .onCompletion().onCompleteOnly()
                        .onWhen(header("CamelKafkaManualCommit"))
                            .bean(KafkaDefaultConsumerManualCommit.class, "process");
            }

        };
    }

}
