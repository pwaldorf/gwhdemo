package com.pw.kafkadefault.producer.default1.routetemplate;

import com.pw.kafkadefault.producer.default1.configuration.KafkaDefaultProducerProperties;
import com.pw.support1.route.GwhEndpointRouteBuilderExtension;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.producer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultWriterTemplate extends EndpointRouteBuilder implements GwhEndpointRouteBuilderExtension {

    private final KafkaDefaultProducerProperties defaultProperties;
    public KafkaDefaultWriterTemplate(KafkaDefaultProducerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("kafka_writer_v1")
        .templateParameter("directName", "writeeventstore")
        .templateParameter("topic")
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "20")
        .templateParameter("producerBatchSize", "65536")
        .templateParameter("compressionCodec", "snappy")
        .from("direct:{{directName}}")
        .routePolicy(getRoutePoliciesByAnnotation(defaultProperties.getDefaultRoutePolicy()))
        .routeConfigurationId(defaultProperties.getDefaultRouteConfigurationPattern())
        .to(getProducerEndpointRouteBuilderByName(defaultProperties.getDefaultEndpoint()).getProducerEndpoint());

    }
}
