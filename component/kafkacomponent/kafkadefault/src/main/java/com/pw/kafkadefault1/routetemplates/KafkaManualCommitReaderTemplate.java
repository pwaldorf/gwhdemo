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
        .templateParameter("transactionRef","txRequiredActiveMqTest")
        .templateParameter("isolationLevel","read_committed")
        .templateParameter("directName")
        .from(getConsumerEndpointRouteBuilderByName(defaultProperties.getManualCommitEndpoint()).getConsumerEndpoint())
        .routePolicy(getRoutePoliciesByAnnotation(defaultProperties.getDefaultRoutePolicy()))
        .routeConfigurationId(defaultProperties.getDefaultRouteConfigurationPattern());
        // .onCompletion().onCompleteOnly().onWhen(header("CamelKafkaManualCommit"))
        //                .bean(KafkaDefaultConsumerManualCommit.class, "process")
        //                .end()
        // .setHeader("GWHOriginalMessageID").simple("${headerAs('kafka.OFFSET', String)}");
        // .to("direct:logger")
        // .choice()
        //     .when(header("GWHMessageType").isNull())
        //         .setHeader("GWHMessageType").simple("original", String.class)
        //         .to("direct:{{directName}}")
        //     .when(header("GWHMessageType").isEqualTo("original"))
        //     .log(LoggingLevel.INFO, "Continuing current Route ${routeId} for Message Type ${header.GWHMessageType}")
        //         .to("direct:{{directName}}")
        //     .when(header("GWHMessageResendRoutes").in("${routeId}","ALL"))
        //     .log(LoggingLevel.DEBUG, "Continuing current Route ${routeId} for Message Type ${header.GWHMessageType}")
        //         .to("direct:{{directName}}")
        //     .end();
    }

}
