package com.pw.kafkadefault1.routetemplates;

import com.pw.kafkadefault1.configurations.KafkaDefaultProperties;
import com.pw.support1.route.GwhAbstractRouteTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaTransactionalWriterTemplate extends GwhAbstractRouteTemplate {

    private final KafkaDefaultProperties kafkaDefaultProperties;

    public KafkaTransactionalWriterTemplate(KafkaDefaultProperties kafkaDefaultProperties) {
        this.kafkaDefaultProperties = kafkaDefaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("kafka_writer_tx_v1")
        .templateParameter("directName")
        .templateParameter("topic")
        .templateParameter("transactionalId", "gwh01")
        .templateParameter("idempotence", "true")
        .templateParameter("retries", "5")
        .templateParameter("maxInflightRequests", "1")
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "0")
        .from("direct:{{directName}}")
        .to(getProducerEndpointRouteBuilderByName(kafkaDefaultProperties.getTransactionalProducerEndpoint()).getProducerEndpoint());

    }

}
