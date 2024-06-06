package com.pw.ftpdefault1.consumer.configurations;

import com.pw.support1.model.GwhRoutePolicies;
import com.pw.support1.route.GwhRoutePolicyBuilder;
import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.default1.consumer.enabled", havingValue = "true")
public class FtpConsumerRoutePolicies implements GwhRoutePolicyBuilder {

    private final GwhRoutePolicies routePolicies = new GwhRoutePolicies();
    private final FtpConsumerProperties ftpConsumerProperties;

    public FtpConsumerRoutePolicies(FtpConsumerProperties ftpConsumerProperties) {
        this.ftpConsumerProperties = ftpConsumerProperties;
    }

    public void init() {
        CronScheduledRoutePolicy cronScheduledRoutePolicy = new CronScheduledRoutePolicy();
        cronScheduledRoutePolicy.setRouteStartTime(ftpConsumerProperties.getRouteStartTime());
        routePolicies.addRoutePolicies(cronScheduledRoutePolicy);
    }

    @Override
    public GwhRoutePolicies getRoutePolicies() {
        return routePolicies;
    }
}
