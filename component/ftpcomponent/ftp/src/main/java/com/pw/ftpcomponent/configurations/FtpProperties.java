package com.pw.ftpcomponent.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.ftp")
public class FtpProperties {

    String routeStartTime = "1 * * * * ?";
    String protocol = "FTP";

}
