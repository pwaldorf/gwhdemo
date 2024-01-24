package com.pw.jmscomponents.routetemplates;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(value = "gtw.framework.component.jms.enabled", havingValue = "true", matchIfMissing = false)
public class JmsReaderTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        routeTemplate("jms_reader")
        .templateParameter("queue")
        .templateParameter("transactionRef", "txRequired")
        .templateParameter("directname")        
        .from( new StringBuilder("jmsConsumerTransacted:queue:")
                        .append("{{queue}}")
                        .toString())
        .transacted("{{transactionRef}}")        
        .setHeader("GWHOriginalMessageID").simple("${headerAs('JMSMessageID', String)}")        
        .setHeader("GWHOriginalCorrelationID").simple("${headerAs('JMSCorrelationID', String)}")
        .setHeader("GWHOriginalDestination", simple("${headerAs('JMSDestination', String)}"))            
        .to("direct:logger")
        .removeHeaders("JMS*")
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