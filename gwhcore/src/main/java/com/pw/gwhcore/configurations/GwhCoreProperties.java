package com.pw.gwhcore.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.service")
public class GwhCoreProperties {

    private String profile;
    private String configTableName = "gwh_configs";
    private String configKeyColumnName = "config_key";
    private String configValueColumnName = "config_value";
    private String configNameColumnName = "config_name";

}
