package com.pw.ftpdefaultcomponent.routetemplates;

import org.apache.camel.builder.EndpointConsumerBuilder;
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
    @Qualifier("ftpDefaultRoutePolicies")
    List<RoutePolicy> routePolicies;

    @Autowired
    @Qualifier("ftpCronScheduledRoutePolicy")
    CronScheduledRoutePolicy ftpCronScheduledRoutePolicy;

    @Autowired
    @Qualifier("ftpEndpointConsumer")
    EndpointConsumerBuilder ftpEndpointConsumerBuilder;

    @Override
    public void configure() throws Exception {

        if (routePolicies == null) {
            routePolicies = new ArrayList<>();
            routePolicies.add(ftpCronScheduledRoutePolicy);
        }

        routeTemplate("ftp_reader_v1")
            .templateParameter("fileComponent", "ftp")
            .templateParameter("directName")
            .templateParameter("user")
            .templateParameter("password")
            .templateParameter("server")
            .templateParameter("port", "21")
            .templateParameter("directory", "pub")
            .templateParameter("fileName")
            .templateParameter("delay", "10000")
            .templateParameter("streamDownload", "true")
            .templateParameter("stepwise", "false")
            .templateParameter("bridgeErrorHandler", "true")
            .templateParameter("completedFolder", "completed")
            .templateParameter("errorFolder", "error")
            .from(ftpEndpointConsumerBuilder)
                .routePolicy(routePolicies.toArray(RoutePolicy[]::new)).noAutoStartup()
                .routeConfigurationId(".*ftpError.*,.*ftpConfig.*")
                .to("direct:{{directName}}");


    }
}