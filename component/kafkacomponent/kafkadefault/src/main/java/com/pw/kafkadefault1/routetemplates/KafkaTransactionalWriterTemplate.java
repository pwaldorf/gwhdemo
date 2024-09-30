package com.pw.kafkadefault1.routetemplates;

import com.pw.kafkadefault1.configurations.KafkaDefaultProducerProperties;
import com.pw.support1.route.GwhEndpointRouteBuilderExtension;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaTransactionalWriterTemplate extends EndpointRouteBuilder implements GwhEndpointRouteBuilderExtension {

    private final KafkaDefaultProducerProperties defaultProperties;

    public KafkaTransactionalWriterTemplate(KafkaDefaultProducerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("kafka_writer_tx_v1")
        .templateParameter("directName", "writetransactionaleventstore")
        .templateParameter("topic")
        .templateParameter("transactionalId", "gwh01")
        .templateParameter("idempotence", "true")
        .templateParameter("retries", "5")
        .templateParameter("maxInflightRequests", "1")
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "0")
        .templateParameter("maxBlocksMs", "60000")
        .from("direct:{{directName}}")
        .routePolicy(getRoutePoliciesByAnnotation(defaultProperties.getDefaultRoutePolicy()))
        .routeConfigurationId(defaultProperties.getDefaultRouteConfigurationPattern())
        .to(getProducerEndpointRouteBuilderByName(defaultProperties.getTransactionalEndpoint()).getProducerEndpoint());

    }

}
