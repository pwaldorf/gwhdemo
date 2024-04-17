package com.pw.kafkadefault.routetemplates;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.kafka;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.beans.KafkaDefaultConsumerManualCommit;
import com.pw.kafkadefault.components.KafkaDefaultComponents;


@Component
@ConditionalOnBean(KafkaDefaultComponents.class)
public class KafkaDefaultReaderTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Kafka Reader Template
        routeTemplate("kafka_reader_manual_commit_v1")
        .templateParameter("topic")
        .templateParameter("groupId")
        .templateParameter("transactionRef","txRequiredActiveMqTest")
        .templateParameter("isolationLevel","read_committed")
        .templateParameter("directname")
        // .from( new StringBuilder("kafkaDefaultConsumer:")
        //                  .append("{{topic}}")
        //                  .append("?groupId={{groupId}}")
        //                  .append("&allowManualCommit=true")
        //                  .append("&autoCommitEnable=false")
        //                  .append("&isolationLevel={{isolationLevel}}")
        //                  .toString())
        .from(kafka("kafkaDefaultConsumer", "{{topic}}")
                .groupId("{{groupId}}")
                .allowManualCommit(true)
                .autoCommitEnable(false)
                .advanced()
                .isolationLevel("{{isolationLevel}}"))
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

        // Kafka Reader Template
        routeTemplate("kafka_reader_auto_commit_v1")
        .templateParameter("topic")
        .templateParameter("groupId")
        .templateParameter("transactionRef","txRequiredActiveMqTest")
        .templateParameter("isolationLevel","read_committed")
        .templateParameter("directname")
        // .from( new StringBuilder("kafkaDefaultConsumer:")
        //                  .append("{{topic}}")
        //                  .append("?groupId={{groupId}}")
        //                  .append("&allowManualCommit=false")
        //                  .append("&autoCommitEnable=true")
        //                  .append("&isolationLevel={{isolationLevel}}")
        //                  .toString())
        .from(kafka("kafkaDefaultConsumer", "{{topic}}")
                .groupId("{{groupId}}")
                .allowManualCommit(false)
                .autoCommitEnable(true)
                .advanced()
                .isolationLevel("{{isolationLevel}}"))
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
