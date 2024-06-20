package com.pw.gwhcore1.gwhproperties2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.properties.db")
public class GwhDatabaseProperties {
    private String table;
    private String keyColumn;
    private String valueColumn;
    private String profileColumn;
    private String regionColumn;

    private String url;
    private String userName;
    private String password;
}
