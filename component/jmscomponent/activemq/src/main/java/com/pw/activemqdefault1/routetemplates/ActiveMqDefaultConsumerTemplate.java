package com.pw.activemqdefault1.routetemplates;

import com.pw.activemqdefault1.configurations.ActiveMqDefaultProperties;
import com.pw.support1.route.GwhAbstractRouteTemplate;
import org.apache.camel.LoggingLevel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default1.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultConsumerTemplate extends GwhAbstractRouteTemplate {

    private final ActiveMqDefaultProperties activeMqDefaultProperties;

    public ActiveMqDefaultConsumerTemplate(ActiveMqDefaultProperties activeMqDefaultProperties) {
        this.activeMqDefaultProperties = activeMqDefaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_reader_v1")
        .templateParameter("queue")
        .templateParameter("directname")
        .from(getConsumerEndpointRouteBuilderByName(activeMqDefaultProperties.getDefaultConsumerEndpoint()).getConsumerEndpoint())
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