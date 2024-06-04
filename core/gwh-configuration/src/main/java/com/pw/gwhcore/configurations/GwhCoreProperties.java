package com.pw.gwhcore.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

//@Setter
//@Getter
//@Configuration
//@ConfigurationProperties(prefix = "gwh.c.service")
public class GwhCoreProperties {

    private String profile;

}
