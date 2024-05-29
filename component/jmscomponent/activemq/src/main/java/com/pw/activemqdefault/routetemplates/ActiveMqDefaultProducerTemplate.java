package com.pw.activemqdefault.routetemplates;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.pw.activemqdefault.configurations.ActiveMqProducerEndpointBuilder;
import com.pw.activemqdefault.configurations.ActiveMqProducerRoutePolicies;
import com.pw.support.route.AbstractProducerTemplate;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.activemq.producer.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultProducerTemplate extends AbstractProducerTemplate<ActiveMqProducerEndpointBuilder> {

    public ActiveMqDefaultProducerTemplate(ActiveMqProducerEndpointBuilder endpointProducerBuilder, @Nullable ActiveMqProducerRoutePolicies routePolicies) {
        super(endpointProducerBuilder, routePolicies);
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_writer_v1")
        .templateParameter("directname")
        .templateParameter("queue")
        .templateParameter("targetclient","0")
        .from("direct:{{directname}}")
        .setHeader("CamelJmsDestinationName", constant("queue://{{queue}}?targetclient={{targetclient}}"))
        .to(getEndpointProducerBuilder().getProducerEndpoint());

    }

}
