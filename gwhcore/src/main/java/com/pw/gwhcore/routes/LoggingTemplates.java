package com.pw.gwhcore.routes;

import org.apache.camel.LoggingLevel;
//import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.parser.gwhparser.parser.config.ParserFormat;
import com.pw.gwhcore.parser.ParserDataFormat;

@Component
public class LoggingTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        ParserDataFormat parser = new ParserDataFormat();
        parser.setFormat(ParserFormat.FIXEDLENGTH);

                        
        from("timer:foo?period=10000")
        .setBody(constant("PHILIP    WALDORF   26"))
        .unmarshal(parser)
        .log(LoggingLevel.INFO, org.slf4j.LoggerFactory.getLogger("com.mycompany.mylogger"), "Sending message ${body}");

        // templatedRoute("message_logger")        
        // .parameter("directname", "logger")        
        // .routeId("message_logger");
        
    }
    
}
