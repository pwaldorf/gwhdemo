package com.pw.kafkacomponents.routetemplates;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gtw.framework.component.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaWriterTemplates extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        routeTemplate("kafka_writer")        
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
