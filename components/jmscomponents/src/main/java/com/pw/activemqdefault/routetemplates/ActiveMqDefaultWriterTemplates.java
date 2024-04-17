package com.pw.activemqdefault.routetemplates;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.pw.activemqdefault.components.ActiveMqDefaultComponent;

@Component
@ConditionalOnBean(ActiveMqDefaultComponent.class)
public class ActiveMqDefaultWriterTemplates extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqdefault_writer_tx_v1")
        .templateParameter("directname")
        .templateParameter("queue")
        .templateParameter("targetclient","0")
        .from("direct:{{directname}}")
        .setHeader("CamelJmsDestinationName", constant("queue://{{queue}}?targetclient={{targetclient}}"))
        .to(new StringBuilder("activeMqDefaultProducerTx:queue:")
                    .append("{{queue}}")
                    .toString());

    }

}
