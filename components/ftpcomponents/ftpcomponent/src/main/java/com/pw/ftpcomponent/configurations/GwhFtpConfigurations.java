package com.pw.ftpcomponent.configurations;

import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GwhFtpConfigurations {

    @Value("${gwh.framework.component.ftp.route.start.time:1 * * * * ?}")
    String routeStartTime;

    @Bean("ftpCronScheduledRoutePolicy")
    public CronScheduledRoutePolicy cronScheduledRoutePolicy() {
        CronScheduledRoutePolicy cronScheduledRoutePolicy = new CronScheduledRoutePolicy();
        cronScheduledRoutePolicy.setRouteStartTime(routeStartTime);
        return cronScheduledRoutePolicy;
    }
}
