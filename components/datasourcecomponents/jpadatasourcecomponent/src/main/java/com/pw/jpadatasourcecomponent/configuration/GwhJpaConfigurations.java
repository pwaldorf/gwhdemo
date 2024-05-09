package com.pw.jpadatasourcecomponent.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.pw.jpapropertiescomponent.jpa.repository",
                                       "com.pw.jparoutesloadercomponent.jpa.repository",
                                       "com.pw.jparoutetemplatesloadercomponent.jpa.repository",
                                       "com.pw.gwhcore.jpa.repository"},
                       entityManagerFactoryRef = "gwhEntityManagerFactory",
                       transactionManagerRef = "gwhTransactionManager")
//@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhJpaConfigurations {

    private Properties gwhJpaProperties;

    private JpaVendorAdapter gwhJpaVendorAdapter;

    private DataSource gwhDataSource;

    public GwhJpaConfigurations(@Qualifier("gwhJpaProperties") Properties gwhJpaProperties,
                                       @Qualifier("gwhJpaVendorAdapter") JpaVendorAdapter gwhJpaVendorAdapter,
                                       DataSource gwhDataSource) {
        this.gwhJpaProperties = gwhJpaProperties;
        this.gwhJpaVendorAdapter = gwhJpaVendorAdapter;
        this.gwhDataSource = gwhDataSource;
    }

    @Bean(name = "gwhEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException{
        final LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(gwhDataSource);
        sessionFactory.setPackagesToScan(new String[] {"com.pw.jpapropertiescomponent.jpa.model",
                                                       "com.pw.jparoutesloadercomponent.jpa.model",
                                                       "com.pw.jparoutetemplatesloadercomponent.jpa.model",
                                                       "com.pw.gwhcore.jpa.model"});
        sessionFactory.setJpaVendorAdapter(gwhJpaVendorAdapter);
        sessionFactory.setJpaProperties(gwhJpaProperties);
        return sessionFactory;
    }

    @Bean(name = "gwhTransactionManager")
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean(name = "gwhExceptionTranslationPostProcessor")
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
