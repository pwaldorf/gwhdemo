package com.pw.gwhdeployment.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
// import org.springframework.context.annotation.Profile;

@Component
// @Profile("local")
public class LoggingTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // This creates a test message for the local routes
        from("timer:foo?period=0&delay=-1&repeatCount=1")
        // .setBody(constant("This is a test MQ Message1"))
        //  .to("jms:queue:test.queue1")
        // .setBody(constant("This is a test MQ Message2"))
        //  .to("jms:queue:test.queue1");
            .setBody(constant("<foo><bar>cheese</bar></foo>"))
                .to("jms:queue:test.queue1");

    }
}
