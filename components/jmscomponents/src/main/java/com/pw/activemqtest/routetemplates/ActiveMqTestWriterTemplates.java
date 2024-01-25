package com.pw.activemqtest.routetemplates;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.activemqtest.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqTestWriterTemplates extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        routeTemplate("activemqtest_writer_tx_v1")
        .templateParameter("directname")        
        .templateParameter("queue")
        .templateParameter("targetclient","0")
        .from("direct:{{directname}}")        
        .setHeader("CamleJmsDestinationName", constant("queue://{{queue}}?targetclient={{targetclient}}"))
        .to(new StringBuilder("activeMqTestProducerTx:queue:")
                    .append("{{queue}}")
                    .toString());

    }
    
}
