package com.pw.activemq.gwhdefault.consumer.default1.routetemplate;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.activemq.gwhdefault.consumer.default1.configuration.ActiveMqDefaultConsumerProperties;
import com.pw.support1.route.GwhEndpointRouteBuilderExtension;

@Component
@ConditionalOnProperty(value = "gwh.component.activemq.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqTransactedConsumerTemplate extends EndpointRouteBuilder implements GwhEndpointRouteBuilderExtension {

    private final ActiveMqDefaultConsumerProperties defaultProperties;

    public ActiveMqTransactedConsumerTemplate(ActiveMqDefaultConsumerProperties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_consumer_tx_v1")
        .templateParameter("queue")
        .templateParameter("directName")
        .templateParameter("jmsMessageType", "Text")
        .templateParameter("cacheLevelName", "CACHE_CONSUMER")
        .templateParameter("concurrentConsumers", "1")
        .templateParameter("maxConcurrentConsumers", "1")
        .templateParameter("asyncConsumer", "false")
        .templateParameter("transaction", "txRequiredActiveMqDefault")
        .from(getConsumerEndpointRouteBuilderByName(defaultProperties.getDefaultConsumerEndpoint()).getConsumerEndpoint())
        .routePolicy(getRoutePoliciesByAnnotation(defaultProperties.getDefaultRoutePolicy()))
        .routeConfigurationId(defaultProperties.getDefaultRouteConfigurationPattern())
        .transacted("txRequiredActiveMqDefault")
        // .bean(XPathTest.class, "test")
        .to("direct:{{directName}}")
        .routeGroup("Consumer");
    }

}
