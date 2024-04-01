package com.pw.ftpcomponent.routetemplates;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.apache.camel.spi.RoutePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.reader.enabled", havingValue = "true", matchIfMissing = false)
public class FtpReaderTemplate extends RouteBuilder {

    @Autowired(required = false)
    List<RoutePolicy> routePolicies;

    @Autowired
    @Qualifier("ftpCronScheduledRoutePolicy")
    CronScheduledRoutePolicy ftpCronScheduledRoutePolicy;

    @Override
    public void configure() throws Exception {

        if (routePolicies == null) {
            routePolicies = new ArrayList<>();
            routePolicies.add(ftpCronScheduledRoutePolicy);
        }

        routeTemplate("ftpRouteTemplate_v1")
            .templateParameter("fileComponent", "ftp")
            .templateParameter("directName")
            .templateParameter("user")
            .templateParameter("server")
            .templateParameter("port", "21")
            .templateParameter("password")
            .templateParameter("fileName")
            .templateParameter("delay", "10000")
            .templateParameter("streamdownload", "true")
            .templateParameter("stepwise", "false")
            .templateParameter("completedFolder", "completed")
            .templateParameter("failedFolder", "error")
            .from(new StringBuilder("{{fileComponent}}://")
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
                            .append("&delay={{delay}}")
                            .append("&move={{completedFolder}}/${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}")
                            .append("&moveFailed={{failedFolder}}/${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}")
                            .toString())
            .routePolicy(routePolicies.toArray(RoutePolicy[]::new)).noAutoStartup()
            .routeConfigurationId(".*ftpError.*,.*ftpConfig.*")
            .to("direct:{{directName}}");


    }
}