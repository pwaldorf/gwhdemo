package com.pw.ftpdefault1.consumer.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.ftp.default1.consumer")
public class FtpConsumerProperties {

    private String routeStartTime = "1 * * * * ?";
    private String protocol = "sftp";
    private String defaultConsumerEndpoint = "ftpConsumerEndpoint";

}
