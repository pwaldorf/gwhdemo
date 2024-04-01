package com.pw.activemqtest.routetemplates;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.activemqtest.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqTestWriterTransactedTemplate extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        routeTemplate("activemqtest_writer_tx_route_v1")
        .templateParameter("directname")
        .templateParameter("queue")
        .templateParameter("transactionRef", "txRequiredActiveMqTest")
        .templateParameter("targetclient","0")
        .from("direct:{{directname}}")
        .transacted("{{transactionRef}}")
        .setHeader("CamelJmsDestinationName", constant("queue://{{queue}}?targetclient={{targetclient}}"))
        .to(new StringBuilder("activeMqTestProducerTx:queue:")
                    .append("{{queue}}")
                    .toString());
    }

}
