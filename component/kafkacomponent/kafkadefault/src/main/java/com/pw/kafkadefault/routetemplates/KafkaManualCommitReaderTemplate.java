package com.pw.kafkadefault.routetemplates;

import org.apache.camel.LoggingLevel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.beans.KafkaDefaultConsumerManualCommit;
import com.pw.kafkadefault.components.KafkaDefaultComponents;
import com.pw.kafkadefault.configurations.KafkaConsumerEndpointBuilder;
import com.pw.kafkadefault.configurations.KafkaConsumerRoutePolicies;
import com.pw.support.route.AbstractConsumerTemplate;

@Component
@ConditionalOnBean(KafkaDefaultComponents.class)
public class KafkaManualCommitReaderTemplate extends AbstractConsumerTemplate<KafkaConsumerEndpointBuilder> {

    public KafkaManualCommitReaderTemplate(KafkaConsumerEndpointBuilder endpointConsumerBuilder, @Nullable KafkaConsumerRoutePolicies routePolicies) {
        super(endpointConsumerBuilder, routePolicies);
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
        .templateParameter("directname")
        .from(getEndpointConsumerBuilder().getConsumerEndpoint())
        .onCompletion().onCompleteOnly().onWhen(header("CamelKafkaManualCommit"))
                       .bean(KafkaDefaultConsumerManualCommit.class, "process")
                       .end()
        .setHeader("GWHOriginalMessageID").simple("${headerAs('kafka.OFFSET', String)}")
        .to("direct:logger")
        .choice()
            .when(header("GWHMessageType").isNull())
                .setHeader("GWHMessageType").simple("original", String.class)
                .to("direct:{{directname}}")
            .when(header("GWHMessageType").isEqualTo("original"))
            .log(LoggingLevel.INFO, "Continuing current Route ${routeId} for Message Type ${header.GWHMessageType}")
                .to("direct:{{directname}}")
            .when(header("GWHMessageResendRoutes").in("${routeId}","ALL"))
            .log(LoggingLevel.DEBUG, "Continuing current Route ${routeId} for Message Type ${header.GWHMessageType}")
                .to("direct:{{directname}}")
            .end();
    }

}
