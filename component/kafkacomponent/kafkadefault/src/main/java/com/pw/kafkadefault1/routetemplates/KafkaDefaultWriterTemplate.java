package com.pw.kafkadefault1.routetemplates;

import com.pw.support1.route.GwhAbstractRouteTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault1.components.KafkaDefaultComponents;
import com.pw.kafkadefault1.configurations.KafkaProducerEndpointBuilder;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultWriterTemplate extends GwhAbstractRouteTemplate<KafkaProducerEndpointBuilder> {

    public KafkaDefaultWriterTemplate(@Qualifier("kafkaDefaultProducerEndpoint") KafkaProducerEndpointBuilder endpointProducerBuilder) {
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
        .to(getEndpointRouteBuilder().getProducerEndpoint());
    }
}
