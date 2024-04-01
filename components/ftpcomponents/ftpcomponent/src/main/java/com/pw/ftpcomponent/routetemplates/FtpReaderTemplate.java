package com.pw.activemqtest.routetemplates;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import java.util.ArrayList;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.reader.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqTestReaderTemplates extends RouteBuilder {

    @Autowired
    List<RoutePolicy> routePolicies;

    @Override
    public void configure() throws Exception {

        routeTemplate("fileRouteTemplate")
            .templateParameter("filecomponent", "ftp")
            .templateParameter("directid", "splitmqsend")
            .templateParameter("user")
            .templateParameter("server")
            .templateParameter("port", "21")
            .templateParameter("password")
            .templateParameter("filename")
            .templateParameter("schedule", "0/20 * * * * ?")
            .templateParameter("streamdownload", "true")
            .templateParameter("stepwise", "false")
            .from(new StringBuilder("{{filecomponent}}://")
                            .append("{{user}}@")
                            .append("{{server}}:")
                            .append("{{port}}/pub")
                            .append("?password={{password}}")
                            .append("&fileName={{filename}}")
                            .append("&streamDownload={{streamdownload}}")
                            .append("&stepwise={{stepwise}}")
                            .append("&bridgeErrorHandler=true")
                            .append("&backoffErrorThreshold=1")
                            .append("&backoffMultiplier=10")
                            .append("&delay=10000")
                            //.append("&scheduler=quartz")
                            //.append("&scheduler.cron={{schedule}}")
                            .append("&move=completed/${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}")
                            .append("&moveFailed=error/${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}")
                            .toString())
            .routePolicyRef("fileResumeRoutePolicy,cronScheduledRoutePolicy").noAutoStartup()
            .to("direct:{{directname}}");


    }
}