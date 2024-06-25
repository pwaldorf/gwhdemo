package com.pw.ftpdefault1.consumer.configurations;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.apache.camel.spi.RoutePolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.ftp.default1.consumer.enabled", havingValue = "true")
public class FtpConsumerConfigurations {

    private final FtpConsumerProperties ftpProperties;

    public FtpConsumerConfigurations(FtpConsumerProperties ftpProperties) {
        this.ftpProperties = ftpProperties;
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
        cronScheduledRoutePolicy.setRouteStartTime(ftpProperties.getRouteStartTime());
        return cronScheduledRoutePolicy;
    }
}
