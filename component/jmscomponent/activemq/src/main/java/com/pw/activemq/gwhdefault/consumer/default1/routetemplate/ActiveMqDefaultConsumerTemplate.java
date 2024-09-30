package com.pw.activemq.gwhdefault.consumer.default1.routetemplate;

import com.pw.activemq.gwhdefault.consumer.default1.configuration.ActiveMqDefaultConsumerProperties;
import com.pw.support1.route.GwhEndpointRouteBuilderExtension;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.activemq.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultConsumerTemplate extends EndpointRouteBuilder implements GwhEndpointRouteBuilderExtension {

    private final ActiveMqDefaultConsumerProperties defaultProperties;

    public ActiveMqDefaultConsumerTemplate(ActiveMqDefaultConsumerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_reader_v1")
        .templateParameter("queue")
        .templateParameter("directName")
        .from(getConsumerEndpointRouteBuilderByName(defaultProperties.getDefaultConsumerEndpoint()).getConsumerEndpoint())
        .routePolicy(getRoutePoliciesByAnnotation(defaultProperties.getDefaultRoutePolicy()))
        .routeConfigurationId(defaultProperties.getDefaultRouteConfigurationPattern())
        .to("direct:{{directName}}")
        .routeGroup("Consumer");
    }
}