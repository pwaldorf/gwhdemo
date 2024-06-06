package com.pw.kafkadefault1.routetemplates;

import com.pw.support1.route.GwhAbstractRouteTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault1.configurations.KafkaProducerEndpointBuilder;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaTransactionalWriterTemplate extends GwhAbstractRouteTemplate<KafkaProducerEndpointBuilder> {

    public KafkaTransactionalWriterTemplate(@Qualifier("kafkaTransactedProducerEndpoint") KafkaProducerEndpointBuilder endpointProducerBuilder) {
        super(endpointProducerBuilder);
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
        .to(getEndpointRouteBuilder().getProducerEndpoint());

    }

}
