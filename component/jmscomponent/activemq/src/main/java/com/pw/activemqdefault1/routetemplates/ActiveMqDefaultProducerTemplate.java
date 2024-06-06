package com.pw.activemqdefault1.routetemplates;

import com.pw.support1.route.GwhAbstractRouteTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.activemqdefault1.configurations.ActiveMqProducerEndpointBuilder;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultProducerTemplate extends GwhAbstractRouteTemplate<ActiveMqProducerEndpointBuilder> {

    public ActiveMqDefaultProducerTemplate(ActiveMqProducerEndpointBuilder endpointProducerBuilder) {
        super(endpointProducerBuilder);
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_writer_v1")
        .templateParameter("directname")
        .templateParameter("queue")
        .templateParameter("targetclient","0")
        .from("direct:{{directname}}")
        .setHeader("CamelJmsDestinationName", constant("queue://{{queue}}?targetclient={{targetclient}}"))
        .to(getEndpointRouteBuilder().getProducerEndpoint());

    }

}
