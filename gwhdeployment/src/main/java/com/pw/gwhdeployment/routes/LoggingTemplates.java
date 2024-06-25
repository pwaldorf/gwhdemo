package com.pw.gwhdeployment.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("local")
public class LoggingTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // This creates a test message for the local routes
        from("timer:foo?period=10000")
        .routeId("test_message_route")
        .setBody(constant("This is a test MQ Message"))
        //  .to("activeMqDefaultProducer:queue:test.queue1");
        .to("jms:queue:test.queue2");

    }

}
