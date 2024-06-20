package com.pw.ftpdefault1.consumer.configurations;

import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.apache.camel.spi.RoutePolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.default1.consumer.enabled", havingValue = "true")
public class FtpCronRoutePolicy implements FtpConsumerRoutePolicyBuilder {

    private final FtpConsumerProperties ftpConsumerProperties;

    public FtpCronRoutePolicy(FtpConsumerProperties ftpConsumerProperties) {
        this.ftpConsumerProperties = ftpConsumerProperties;
    }

    @Override
    public RoutePolicy getRoutePolicies() {
        CronScheduledRoutePolicy cronScheduledRoutePolicy = new CronScheduledRoutePolicy();
        cronScheduledRoutePolicy.setRouteStartTime(ftpConsumerProperties.getRouteStartTime());
        return cronScheduledRoutePolicy;
    }
}
