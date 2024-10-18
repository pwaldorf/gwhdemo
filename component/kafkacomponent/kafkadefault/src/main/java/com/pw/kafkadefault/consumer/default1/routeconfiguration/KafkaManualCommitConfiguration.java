package com.pw.kafkadefault.consumer.default1.routeconfiguration;

import org.apache.camel.builder.endpoint.EndpointRouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.consumer.default1.bean.KafkaDefaultConsumerManualCommit;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaManualCommitConfiguration extends EndpointRouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {
        routeConfiguration("kafkaConsumerManualCommit")
            .onCompletion().onCompleteOnly()
                .onWhen(header("CamelKafkaManualCommit"))
                    .bean(KafkaDefaultConsumerManualCommit.class, "process");
    }

}
