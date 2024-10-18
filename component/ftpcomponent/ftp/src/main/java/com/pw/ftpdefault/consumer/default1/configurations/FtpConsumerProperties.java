package com.pw.ftpdefault.consumer.default1.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.ftpdefault.consumer.default1")
public class FtpConsumerProperties {

    private String routeStartTime = "1 * * * * ?";
    private String protocol = "sftp";
    private String defaultConsumerEndpoint = "ftpConsumerEndpoint";
    private String defaultRoutePolicy = "ftp";
    private String defaultRouteConfigurationPattern = ".*ftpError.*,.*ftpConfig.*";

}
