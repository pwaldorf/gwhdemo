package com.pw.kafkadefault1.routeconfigurations;

import org.apache.camel.builder.endpoint.EndpointRouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault1.beans.KafkaDefaultConsumerManualCommit;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaManualCommitConfiguration extends EndpointRouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {
        routeConfiguration("kafkaConsumerManualCommit")
            .onCompletion().onCompleteOnly()
                .onWhen(header("CamelKafkaManualCommit"))
                    .bean(KafkaDefaultConsumerManualCommit.class, "process");
    }

}
