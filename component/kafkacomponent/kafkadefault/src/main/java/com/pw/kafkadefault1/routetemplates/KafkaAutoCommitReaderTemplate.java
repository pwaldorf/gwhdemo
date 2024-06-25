package com.pw.kafkadefault1.routetemplates;

import com.pw.kafkadefault1.configurations.KafkaDefaultProperties;
import com.pw.support1.route.GwhAbstractRouteTemplate;
import org.apache.camel.LoggingLevel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaAutoCommitReaderTemplate extends GwhAbstractRouteTemplate {

    private final KafkaDefaultProperties kafkaDefaultProperties;

    public KafkaAutoCommitReaderTemplate(KafkaDefaultProperties kafkaDefaultProperties) {
        this.kafkaDefaultProperties = kafkaDefaultProperties;
    }

    @Override
    public void configure() throws Exception {

        // Kafka Reader Template
        routeTemplate("kafka_reader_auto_commit_v1")
        .templateParameter("topic")
        .templateParameter("groupId")
        .templateParameter("allowManualCommit", "false")
        .templateParameter("autoCommitEnable", "true")
        .templateParameter("transactionRef","txRequiredActiveMqTest")
        .templateParameter("isolationLevel","read_committed")
        .templateParameter("directName")
        .from(getConsumerEndpointRouteBuilderByName(kafkaDefaultProperties.getAutoCommitConsumerEndpoint()).getConsumerEndpoint())
        .setHeader("GWHOriginalMessageID").simple("${headerAs('kafka.OFFSET', String)}")
        .to("direct:logger")
        .choice()
            .when(header("GWHMessageType").isNull())
                .setHeader("GWHMessageType").simple("original", String.class)
                .to("direct:{{directName}}")
            .when(header("GWHMessageType").isEqualTo("original"))
            .log(LoggingLevel.INFO, "Continuing current Route ${routeId} for Message Type ${header.GWHMessageType}")
                .to("direct:{{directName}}")
            .when(header("GWHMessageResendRoutes").in("${routeId}","ALL"))
            .log(LoggingLevel.DEBUG, "Continuing current Route ${routeId} for Message Type ${header.GWHMessageType}")
                .to("direct:{{directName}}")
            .end();

    }
}
