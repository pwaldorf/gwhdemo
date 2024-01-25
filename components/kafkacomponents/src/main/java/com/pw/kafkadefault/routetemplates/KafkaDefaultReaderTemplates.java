package com.pw.kafkadefault.routetemplates;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.beans.KafkaDefaultConsumerManualCommit;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultReaderTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        // Kafka Reader Template
        routeTemplate("kafka_reader_manual_commit_v1")
        .templateParameter("topic")
        .templateParameter("brokers")
        .templateParameter("groupId")
        .templateParameter("transactionRef","txRequiredActiveMqTest")                
        .templateParameter("isolationLevel","read_committed")
        .templateParameter("directname")
        .from( new StringBuilder("kafka:")
                         .append("{{topic}}")
                         .append("?brokers={{brokers}}")
                         .append("&groupId={{groupId}}")
                         .append("&allowManualCommit=true")
                         .append("&autoCommitEnable=false")
                         .append("&isolationLevel={{isolationLevel}}")
                         .toString())        
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
        .templateParameter("brokers")
        .templateParameter("groupId")
        .templateParameter("transactionRef","txRequiredActiveMqTest")                
        .templateParameter("isolationLevel","read_committed")
        .templateParameter("directname")
        .from( new StringBuilder("kafka:")
                         .append("{{topic}}")
                         .append("?brokers={{brokers}}")
                         .append("&groupId={{groupId}}")
                         .append("&allowManualCommit=false")
                         .append("&autoCommitEnable=true")
                         .append("&isolationLevel={{isolationLevel}}")
                         .toString())                
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
