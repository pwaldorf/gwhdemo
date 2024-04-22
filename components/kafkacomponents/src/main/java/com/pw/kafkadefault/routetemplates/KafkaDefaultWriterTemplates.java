package com.pw.kafkadefault.routetemplates;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.components.KafkaDefaultComponents;

@Component
@ConditionalOnBean(KafkaDefaultComponents.class)
public class KafkaDefaultWriterTemplates extends RouteBuilder{

    @Autowired
    @Qualifier("kafkaEndpointDefaultProducer")
    EndpointProducerBuilder kafkaEndpointDefaultBuilder;

    @Autowired
    @Qualifier("kafkaEndpointTransactedProducer")
    EndpointProducerBuilder kafkaEndpointTransactedBuilder;

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
        .to(kafkaEndpointTransactedBuilder);

        routeTemplate("kafka_writer_v1")
        .templateParameter("directname")
        .templateParameter("topic")
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "0")
        .from("direct:{{directname}}")
        .to(kafkaEndpointDefaultBuilder);

    }
}
