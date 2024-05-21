package com.pw.jpadatasourcecomponent.configuration;

import java.util.Map;
import java.util.Properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pw.support.datasource.GwhDataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.pw.jpapropertiescomponent.jpa.repository",
                                       "com.pw.jparoutesloadercomponent.jpa.repository",
                                       "com.pw.jparoutetemplatesloadercomponent.jpa.repository",
                                       "com.pw.gwhcore.jpa.repository"},
                       entityManagerFactoryRef = "gwhEntityManagerFactory",
                       transactionManagerRef = "gwhTransactionManager")
@ConditionalOnProperty(prefix = "gwh.jpa.db.datasource", name = "className", matchIfMissing = false)
public class GwhJpaConfigurations {

    private GwhJpaDataSourceProperties gwhJpaDataSourceProperties;

    private GwhDataSource gwhDataSource;

    public GwhJpaConfigurations(GwhJpaDataSourceProperties gwhJpaDataSourceProperties,
                                GwhDataSource gwhDataSource) {
        this.gwhJpaDataSourceProperties = gwhJpaDataSourceProperties;
        this.gwhDataSource = gwhDataSource;
    }

    @Bean(name = "gwhEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(gwhDataSource.getDataSource());
        sessionFactory.setPackagesToScan(new String[] {"com.pw.jpapropertiescomponent.jpa.model",
                                                       "com.pw.jparoutesloadercomponent.jpa.model",
                                                       "com.pw.jparoutetemplatesloadercomponent.jpa.model",
                                                       "com.pw.gwhcore.jpa.model"});
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
        Map<String, String> mapProperties = gwhJpaDataSourceProperties.getJpaProperties();
        Properties properties = new Properties();

        mapProperties.forEach((k,v) -> properties.setProperty(k, v));
        return properties;
    }

    public JpaVendorAdapter gwhJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter() {
            {
                setShowSql(gwhJpaDataSourceProperties.isShowSql());
                setGenerateDdl(gwhJpaDataSourceProperties.isGenerateDdl());
                setDatabase(Database.valueOf(gwhJpaDataSourceProperties.getDatabaseType()));
                setDatabasePlatform(gwhJpaDataSourceProperties.getJpaDialect());

            }
        };
    }
}
