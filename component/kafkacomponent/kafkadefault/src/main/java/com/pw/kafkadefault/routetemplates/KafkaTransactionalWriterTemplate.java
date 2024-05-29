package com.pw.kafkadefault.routetemplates;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.components.KafkaDefaultComponents;
import com.pw.kafkadefault.configurations.KafkaProducerEndpointBuilder;
import com.pw.kafkadefault.configurations.KafkaProducerRoutePolicies;
import com.pw.support.route.AbstractProducerTemplate;

@Component
@ConditionalOnBean(KafkaDefaultComponents.class)
public class KafkaTransactionalWriterTemplate extends AbstractProducerTemplate<KafkaProducerEndpointBuilder> {

    public KafkaTransactionalWriterTemplate(@Qualifier("kafkaTransactedProducerEndpoint") KafkaProducerEndpointBuilder endpointProducerBuilder,
                                            @Nullable KafkaProducerRoutePolicies routePolicies) {
        super(endpointProducerBuilder, routePolicies);
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("kafka_writer_tx_v1")
        .templateParameter("directname")
        .templateParameter("topic")
        .templateParameter("transactionalId", "gwh01")
        .templateParameter("idempotence", "true")
        .templateParameter("retries", "5")
        .templateParameter("maxInflightRequests", "1")
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "0")
        .from("direct:{{directname}}")
        .to(getEndpointProducerBuilder().getProducerEndpoint());

    }

}
