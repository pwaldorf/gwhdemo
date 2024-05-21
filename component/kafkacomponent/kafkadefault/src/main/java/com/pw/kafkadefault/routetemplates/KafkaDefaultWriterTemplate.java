package com.pw.kafkadefault.routetemplates;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.components.KafkaDefaultComponents;
import com.pw.support.route.AbstractWriterTemplate;

@Component
@ConditionalOnBean(KafkaDefaultComponents.class)
public class KafkaDefaultWriterTemplate extends AbstractWriterTemplate {

    public KafkaDefaultWriterTemplate(@Qualifier("kafkaEndpointDefaultProducer") EndpointProducerBuilder endpointProducerBuilder) {
        super(endpointProducerBuilder);
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("kafka_writer_v1")
        .templateParameter("directname")
        .templateParameter("topic")
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "0")
        .from("direct:{{directname}}")
        .to(endpointProducerBuilder);
    }
}
