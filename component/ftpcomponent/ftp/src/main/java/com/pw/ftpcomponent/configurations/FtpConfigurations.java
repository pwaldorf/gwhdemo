package com.pw.ftpcomponent.configurations;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.apache.camel.spi.RoutePolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.ftp.reader.enabled", havingValue = "true", matchIfMissing = false)
public class FtpConfigurations {

    FtpProperties ftpDefaultProperties;

    public FtpConfigurations(FtpProperties ftpDefaultProperties) {
        this.ftpDefaultProperties = ftpDefaultProperties;
    }

    @Bean("ftpRoutePolicies")
    public List<RoutePolicy> routePolicies() {
        List<RoutePolicy> routePolicies = new ArrayList<>();
        routePolicies.add(cronScheduledRoutePolicy());
        return routePolicies;
    }

    @Bean("ftpCronScheduledRoutePolicy")
    public CronScheduledRoutePolicy cronScheduledRoutePolicy() {
        CronScheduledRoutePolicy cronScheduledRoutePolicy = new CronScheduledRoutePolicy();
        cronScheduledRoutePolicy.setRouteStartTime(ftpDefaultProperties.routeStartTime);
        return cronScheduledRoutePolicy;
    }
}
