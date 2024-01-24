package com.pw.jmscomponents.routetemplates;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gtw.framework.component.jms.enabled", havingValue = "true", matchIfMissing = false)
public class JmsWriterTemplates extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        routeTemplate("jms_writer")
        .templateParameter("directname")        
        .templateParameter("queue")
        .templateParameter("targetclient","0")
        .from("direct:{{directname}}")        
        .setHeader("CamleJmsDestinationName", constant("queue://{{queue}}?targetclient={{targetclient}}"))
        .to(new StringBuilder("jmsProducerTransacted:queue:")
                    .append("{{queue}}")
                    .toString());

    }
    
}
