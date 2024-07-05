package com.pw.kafkadefault1.routetemplates;

import com.pw.kafkadefault1.configurations.KafkaDefaultProducerProperties;
import com.pw.support1.route.GwhEndpointRouteBuilderExtension;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultWriterTemplate extends EndpointRouteBuilder implements GwhEndpointRouteBuilderExtension {

    private final KafkaDefaultProducerProperties defaultProperties;
    public KafkaDefaultWriterTemplate(KafkaDefaultProducerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("kafka_writer_v1")
        .templateParameter("directName")
        .templateParameter("topic")
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "0")
        .templateParameter("producerEndpoint", "kafkaProducerEndpoint")
        .from("direct:{{directName}}")
        .routePolicy(getRoutePoliciesByAnnotation(defaultProperties.getDefaultRoutePolicy()))
        .routeConfigurationId(defaultProperties.getDefaultRouteConfigurationPattern())
        .log("pjwa: " + getProducerEndpointRouteBuilderByName(defaultProperties.getDefaultEndpoint()).getProducerEndpoint())
        .to(getProducerEndpointRouteBuilderByName(defaultProperties.getDefaultEndpoint()).getProducerEndpoint());

    }
}
