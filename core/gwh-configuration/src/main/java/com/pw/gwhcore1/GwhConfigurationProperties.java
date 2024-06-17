package com.pw.gwhcore1;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.service")
public class GwhConfigurationProperties {

    private String profile = "ALL";
    private String region = "ALL";

}
