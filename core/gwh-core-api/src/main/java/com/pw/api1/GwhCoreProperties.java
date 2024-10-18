package com.pw.api1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gwh.service.core")
public class GwhCoreProperties {

    private String fileterTestMessages = "false";
}
