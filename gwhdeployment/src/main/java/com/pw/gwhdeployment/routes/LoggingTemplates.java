package com.pw.gwhdeployment.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class LoggingTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:foo?period=10000")
        .setBody(constant("PHILIP    WALDORF   26" + "\n" + "AMY       WALDORF   02"))
         .to("jms:queue:mailbox");

    }

}
