package com.pw.activemqdefault.routetemplates;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.pw.activemqdefault.components.ActiveMqProducerComponent;
import com.pw.support.route.AbstractWriterTemplate;

@Component
@ConditionalOnBean(ActiveMqProducerComponent.class)
public class ActiveMqDefaultWriterTemplates extends AbstractWriterTemplate{

    public ActiveMqDefaultWriterTemplates(@Qualifier("activeMqEndpointProducer") EndpointProducerBuilder endpointProducerBuilder) {
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
        .to(endpointProducerBuilder);

    }

}
