package com.pw.gwhcore.jpa.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.core.db.datasource")
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhJpaCoreProperties {

    String className;
    
    String url;

    String userName;
   
    Integer minPoolSize = 0;

    Integer maxPoolSize = 20;

    Integer increment = 2;

    Integer maxIdleTime = 300;

    Integer retryDelay = 600000;

    String jpaDdlAuto = "validate";

    String jpaDialect = "org.hibernate.dialect.MySQLDialect";

    String jpaPhysicalNamingStrategy = "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy";
    
}
