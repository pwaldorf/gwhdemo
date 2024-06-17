package com.pw.activemqdefault1.routetemplates;

import org.apache.camel.LoggingLevel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.activemqdefault1.configurations.ActiveMqConsumerEndpointBuilder;
import com.pw.support1.route.GwhAbstractRouteTemplate;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default1.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultConsumerTemplate extends GwhAbstractRouteTemplate<ActiveMqConsumerEndpointBuilder> {

    public ActiveMqDefaultConsumerTemplate(ActiveMqConsumerEndpointBuilder endpointConsumerBuilder) {
        super(endpointConsumerBuilder);

    }

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_reader_v1")
        .templateParameter("queue")
        .templateParameter("directname")
        .from(super.getEndpointRouteBuilder().getConsumerEndpoint())
        .transacted("txRequiredActiveMqDefault")
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
            .end()
        .routeGroup("Consumer");
    }
}