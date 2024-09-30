package com.pw.kafkadefault1.routetemplates;

import com.pw.kafkadefault1.configurations.KafkaDefaultConsumerProperties;
import com.pw.support1.route.GwhEndpointRouteBuilderExtension;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
// import com.pw.kafkadefault1.beans.KafkaDefaultConsumerManualCommit;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaManualCommitReaderTemplate extends EndpointRouteBuilder implements GwhEndpointRouteBuilderExtension {

    private final KafkaDefaultConsumerProperties defaultProperties;

    public KafkaManualCommitReaderTemplate(KafkaDefaultConsumerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Override
    public void configure() throws Exception {

        // Kafka Reader Template
        routeTemplate("kafka_reader_manual_commit_v1")
        .templateParameter("topic")
        .templateParameter("groupId")
        .templateParameter("allowManualCommit", "true")
        .templateParameter("autoCommitEnable", "false")
        .templateParameter("isolationLevel","read_committed")
        .templateParameter("directName")
        .from(getConsumerEndpointRouteBuilderByName(defaultProperties.getManualCommitEndpoint()).getConsumerEndpoint())
        .routePolicy(getRoutePoliciesByAnnotation(defaultProperties.getDefaultRoutePolicy()))
        .routeConfigurationId(defaultProperties.getDefaultRouteConfigurationPattern())
        .to("direct:{{directName}}");
    }
}
