package com.pw.kafkadefault.routetemplates;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultWriterTemplates extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        routeTemplate("kafka_writer_tx_v1")        
        .templateParameter("directname")
        .templateParameter("topic")
        .templateParameter("brokers")
        .templateParameter("transactionalId", "gwh01")
        .templateParameter("idempotence", "true")
        .templateParameter("retries", "5")
        .templateParameter("maxInflightRequests", "1")
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "0")
        .from("direct:{{directname}}")        
        .to( new StringBuilder("kafka:")
                         .append("{{topic}}")
                         .append("?brokers={{brokers}}")
                         .append("&additional-properties[transactional.id]={{transactionalId}}")
                         .append("&additional-properties[enable.idempotence]={{idempotence}}")
                         .append("&additional-properties[retries]={{retries}}")
                         .append("&additional-properties[max.in.flight.requests.per.connection]={{maxInflightRequests}}")
                         .append("&bufferMemorySize={{bufferMemorySize}}")
                         .append("&lingerMs={{lingerMs}}")
                         .toString());

        routeTemplate("kafka_writer_v1")        
        .templateParameter("directname")
        .templateParameter("topic")
        .templateParameter("brokers")        
        .templateParameter("bufferMemorySize", "33554432")
        .templateParameter("lingerMs", "0")
        .from("direct:{{directname}}")        
        .to( new StringBuilder("kafka:")
                        .append("{{topic}}")
                        .append("?brokers={{brokers}}")                        
                        .append("&bufferMemorySize={{bufferMemorySize}}")
                        .append("&lingerMs={{lingerMs}}")
                        .toString());

    }
    
}
