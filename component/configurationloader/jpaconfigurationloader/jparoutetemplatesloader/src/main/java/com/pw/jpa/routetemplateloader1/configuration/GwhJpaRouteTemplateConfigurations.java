package com.pw.jpa.routetemplateloader1.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = {"com.pw.jpa.routetemplateloader1.jpa.repository"},
                       entityManagerFactoryRef = "gwhEntityManagerFactory",
                       transactionManagerRef = "gwhTransactionManager")
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhJpaRouteTemplateConfigurations {

}