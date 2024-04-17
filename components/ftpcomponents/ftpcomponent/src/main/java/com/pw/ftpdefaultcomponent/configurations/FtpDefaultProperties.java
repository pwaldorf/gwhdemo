package com.pw.ftpdefaultcomponent.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.ftp.default")
public class FtpDefaultProperties {

    String routeStartTime = "1 * * * * ?";

}
