package com.pw.jpa.routepropertiesloader1.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = {"com.pw.jpa.routepropertiesloader1.jpa.repository"},
                       entityManagerFactoryRef = "gwhEntityManagerFactory",
                       transactionManagerRef = "gwhTransactionManager")
@ConditionalOnProperty(value = "gwh.framework.load.route.properties.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhJpaRoutePropertiesConfiguration {

}