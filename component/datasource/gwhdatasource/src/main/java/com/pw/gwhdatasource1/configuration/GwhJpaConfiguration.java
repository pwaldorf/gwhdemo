package com.pw.gwhdatasource1.configuration;

import java.util.Map;
import java.util.Properties;

import com.pw.gwhdatasource1.GwhDataSourceConfiguration;
import com.pw.gwhdatasource1.GwhDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhJpaConfiguration {

    private final GwhDataSourceProperties gwhDataSourceProperties;

    private final GwhDataSourceConfiguration gwhDataSourceConfiguration;

    public GwhJpaConfiguration(GwhDataSourceProperties gwhDataSourceProperties,
                               GwhDataSourceConfiguration gwhDataSourceConfiguration) {
        this.gwhDataSourceProperties = gwhDataSourceProperties;
        this.gwhDataSourceConfiguration = gwhDataSourceConfiguration;
    }

    @Bean(name = "gwhEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(gwhDataSourceConfiguration.getDataSource());
        sessionFactory.setPackagesToScan(new String[] {"com.pw.jpa.*"});
        sessionFactory.setJpaVendorAdapter(gwhJpaVendorAdapter());
        sessionFactory.setJpaProperties(gwhJpaProperties());
        return sessionFactory;
    }

    @Bean(name = "gwhTransactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean(name = "gwhExceptionTranslationPostProcessor")
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    public Properties gwhJpaProperties() {
        Map<String, String> mapProperties = gwhDataSourceProperties.getJpaProperties();
        Properties properties = new Properties();

        mapProperties.forEach((k,v) -> properties.setProperty(k, v));
        return properties;
    }

    public JpaVendorAdapter gwhJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter() {
            {
                setShowSql(gwhDataSourceProperties.isShowSql());
                setGenerateDdl(gwhDataSourceProperties.isGenerateDdl());
                setDatabase(Database.valueOf(gwhDataSourceProperties.getDatabaseType()));
                setDatabasePlatform(gwhDataSourceProperties.getJpaDialect());

            }
        };
    }
}