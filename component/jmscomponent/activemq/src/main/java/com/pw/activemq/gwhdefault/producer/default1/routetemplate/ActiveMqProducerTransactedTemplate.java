package com.pw.activemq.gwhdefault.producer.default1.routetemplate;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.activemq.gwhdefault.producer.default1.configuration.ActiveMqDefaultProducerProperties;
import com.pw.support1.route.GwhEndpointRouteBuilderExtension;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.activemq.producer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqProducerTransactedTemplate extends EndpointRouteBuilder implements GwhEndpointRouteBuilderExtension {

    private final ActiveMqDefaultProducerProperties defaultProperties;

    public ActiveMqProducerTransactedTemplate(ActiveMqDefaultProducerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_writer_v1")
        .templateParameter("directName")
        .templateParameter("queue")
        .templateParameter("transactedref", "txRequiredActiveMqDefault")
        .from("direct:{{directName}}")
        .routePolicy(getRoutePoliciesByAnnotation(defaultProperties.getDefaultRoutePolicy()))
        .routeConfigurationId(defaultProperties.getDefaultRouteConfigurationPattern())
        .transacted("{{transactedref}}")
        .to(getProducerEndpointRouteBuilderByName(defaultProperties.getDefaultProducerEndpoint()).getProducerEndpoint());

    }
}