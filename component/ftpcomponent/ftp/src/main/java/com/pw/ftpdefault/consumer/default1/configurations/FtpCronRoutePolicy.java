package com.pw.ftpdefault.consumer.default1.configurations;

import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.apache.camel.spi.RoutePolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.support1.annotation.GwhRoutePolicy;

@Component
@GwhRoutePolicy(values = {"ftp", "sftp"})
@ConditionalOnProperty(value = "gwh.framework.component.ftpdefault.consumer.default1.enabled", havingValue = "true")
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
