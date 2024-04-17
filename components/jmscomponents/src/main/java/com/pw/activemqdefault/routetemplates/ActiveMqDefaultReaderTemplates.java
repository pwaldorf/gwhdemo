package com.pw.activemqdefault.routetemplates;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.pw.activemqdefault.components.ActiveMqDefaultComponent;


@Component
@ConditionalOnBean(ActiveMqDefaultComponent.class)
public class ActiveMqDefaultReaderTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_reader_tx_v1")
        .templateParameter("queue")
        //.templateParameter("transactionRef", "txRequiredActiveMqDefault")
        .templateParameter("directname")
        .from( new StringBuilder("activeMqDefaultConsumerTx:queue:")
                        .append("{{queue}}")
                        .toString())
        //.transacted("{{transactionRef}}")
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