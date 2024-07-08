package com.pw.gwhdatasource1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConditionalOnProperty(prefix = "gwh.db.datasource", name = "className", matchIfMissing = false)
@ConfigurationProperties(prefix = "gwh.db.datasource")
public class GwhDataSourceProperties {

    private String className;
    private String url;
    private String userName;
    private String password;
    private Integer minPoolSize = 0;
    private Integer maxPoolSize = 20;
    private Integer increment = 2;
    private Integer maxIdleTime = 300;
    private Integer retryDelay = 600000;
    private Integer acquireRetryAttempts = Integer.MAX_VALUE;
    private boolean autoCommitOnClose = false;
    private String databaseType = "MYSQL";
    private boolean showSql = true;
    private boolean generateDdl = false;

    private String jpaDdlAuto = "validate";
    private String jpaDialect = "org.hibernate.dialect.MySQLDialect";
    private String jpaPhysicalNamingStrategy = "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy";
    private Map<String, String> jpaProperties = new HashMap<>();

}
