package com.pw.kafkadefault1.routetemplates;

import com.pw.kafkadefault1.configurations.KafkaDefaultProperties;
import com.pw.support1.route.GwhAbstractRouteTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultWriterTemplate extends GwhAbstractRouteTemplate {

    private final KafkaDefaultProperties kafkaDefaultProperties;
    public KafkaDefaultWriterTemplate(KafkaDefaultProperties kafkaDefaultProperties) {
        this.kafkaDefaultProperties = kafkaDefaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("kafka_writer_v1")
        .templateParameter("directname")
        .templateParameter("topic")
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "0")
        .templateParameter("producerEndpoint", "kafkaDefaultProducerEndpoint")
        .from("direct:{{directname}}")
        .to(getProducerEndpointRouteBuilderByName(kafkaDefaultProperties.getDefaultProducerEndpoint()).getProducerEndpoint());

    }
}
