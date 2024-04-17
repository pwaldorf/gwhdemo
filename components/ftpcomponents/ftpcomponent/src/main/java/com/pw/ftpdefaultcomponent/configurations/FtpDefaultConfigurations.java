package com.pw.ftpdefaultcomponent.configurations;

import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.ftp.reader.enabled", havingValue = "true", matchIfMissing = false)
public class FtpDefaultConfigurations {

    FtpDefaultProperties ftpDefaultProperties;

    public FtpDefaultConfigurations(FtpDefaultProperties ftpDefaultProperties) {
        this.ftpDefaultProperties = ftpDefaultProperties;
    }

    @Bean("ftpCronScheduledRoutePolicy")
    public CronScheduledRoutePolicy cronScheduledRoutePolicy() {
        CronScheduledRoutePolicy cronScheduledRoutePolicy = new CronScheduledRoutePolicy();
        cronScheduledRoutePolicy.setRouteStartTime(ftpDefaultProperties.routeStartTime);
        return cronScheduledRoutePolicy;
    }
}
