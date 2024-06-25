package com.pw.jpa.dataformatloader1.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.pw.jpa.dataformatloader1.jpa.repository"},
                       entityManagerFactoryRef = "gwhEntityManagerFactory",
                       transactionManagerRef = "gwhTransactionManager")
public class GwhDataFormatConfigurations {

}
