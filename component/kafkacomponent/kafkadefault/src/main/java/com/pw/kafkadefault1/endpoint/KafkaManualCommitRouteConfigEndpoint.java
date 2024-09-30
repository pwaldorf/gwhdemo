package com.pw.kafkadefault1.endpoint;

import org.apache.camel.builder.endpoint.EndpointRouteConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pw.kafkadefault1.beans.KafkaDefaultConsumerManualCommit;

// @Configuration
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
