package com.pw.kafkacomponents.routetemplates;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.pw.kafkacomponents.beans.KafkaConsumerManualCommit;


@Component
@ConditionalOnProperty(value = "gtw.framework.component.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaReaderTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        // Kafka Reader Template
        routeTemplate("kafka_reader")
        .templateParameter("topic")
        .templateParameter("brokers")
        .templateParameter("groupId")
        .templateParameter("transactionRef","txRequired")
        .templateParameter("allowManualCommit","true")
        .templateParameter("autoCommitEnabled","false")
        .templateParameter("isolationLevel","read_committed")
        .templateParameter("directname")
        .from( new StringBuilder("kafka:")
                         .append("{{topic}}")
                         .append("?brokers={{brokers}}")
                         .append("&groupId={{groupId}}")
                         .append("&allowManualCommit={{allowManualCommit}}")
                         .append("&autoCommitEnable={{autoCommitEnabled}}")
                         .append("&isolationLevel={{isolationLevel}}")
                         .toString())        
        .onCompletion().onCompleteOnly().onWhen(header("CamelKafkaManualCommit"))                       
                       .bean(KafkaConsumerManualCommit.class, "process")
                       .end()
        .transacted("{{transactionRef}}")        
        .setHeader("GWHOriginalMessageID").simple("${headerAs('kafka.OFFSET', String)}")
        .to("direct:logger")
        //.removeHeaders("kafka*")
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
