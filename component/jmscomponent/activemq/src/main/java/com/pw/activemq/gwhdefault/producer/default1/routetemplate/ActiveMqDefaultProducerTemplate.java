package com.pw.activemq.gwhdefault.producer.default1.routetemplate;

import com.pw.activemq.gwhdefault.producer.default1.configuration.ActiveMqDefaultProducerProperties;
import com.pw.support1.route.GwhEndpointRouteBuilderExtension;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.component.activemq.producer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultProducerTemplate extends EndpointRouteBuilder implements GwhEndpointRouteBuilderExtension {

    private final ActiveMqDefaultProducerProperties defaultProperties;

    public ActiveMqDefaultProducerTemplate(ActiveMqDefaultProducerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_writer_v1")
        .templateParameter("directName")
        .templateParameter("queue")
        .templateParameter("targetClient","0")
        .from("direct:{{directName}}")
        .routePolicy(getRoutePoliciesByAnnotation(defaultProperties.getDefaultRoutePolicy()))
        .routeConfigurationId(defaultProperties.getDefaultRouteConfigurationPattern())
        // .setHeader("CamelJmsDestinationName", constant("queue:{{queue}}?targetClient={{targetClient}}"))
        .to(getProducerEndpointRouteBuilderByName(defaultProperties.getDefaultProducerEndpoint()).getProducerEndpoint());

    }

}
