package com.pw.gwhcore.jpa.configurations;

import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.pw.gwhcore.jpa.repository", entityManagerFactoryRef = "gwhEntityManagerFactory", transactionManagerRef = "gwhCoreTransactionManager")
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhJpaCoreConfigurations {

    GwhJpaCoreProperties gwhJpaCoreProperties;

    public GwhJpaCoreConfigurations(@Autowired GwhJpaCoreProperties gwhJpaCoreProperties) {
        this.gwhJpaCoreProperties = gwhJpaCoreProperties;
    }

    @Bean(name = "gwhCoreDataSource")
    public DataSource dataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(gwhJpaCoreProperties.getClassName());
        dataSource.setJdbcUrl(gwhJpaCoreProperties.getUrl());
        dataSource.setUser(gwhJpaCoreProperties.getUserName());
        dataSource.setPassword(gwhJpaCoreProperties.getPassword());
        dataSource.setMinPoolSize(gwhJpaCoreProperties.getMinPoolSize());
        dataSource.setMaxPoolSize(gwhJpaCoreProperties.getMaxPoolSize());
        dataSource.setAcquireIncrement(gwhJpaCoreProperties.getIncrement());
        dataSource.setMaxIdleTime(gwhJpaCoreProperties.getMaxIdleTime());
        dataSource.setAcquireRetryDelay(gwhJpaCoreProperties.getRetryDelay());
        dataSource.setAcquireRetryAttempts(Integer.MAX_VALUE);
        dataSource.setAutoCommitOnClose(false);
        return dataSource;
    }

    @Bean(name = "gwhEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException{
        final LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] {"com.pw.gwhcore.jpa.model"});
        //JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        sessionFactory.setJpaVendorAdapter(gwhJpaVendorAdapter());
        sessionFactory.setJpaProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean(name = "gwhCoreTransactionManager")
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean(name = "gwhCoreExceptionTranslationPostProcessor")
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", gwhJpaCoreProperties.getJpaDdlAuto());
                //setProperty("hibernate.dialect", gwhJpaCoreProperties.getJpaDialect());
                setProperty("hibernate.physical_naming_strategy", gwhJpaCoreProperties.getJpaPhysicalNamingStrategy());
            }
        };
    }

    private JpaVendorAdapter gwhJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter() {
            {
                setShowSql(true);
                setGenerateDdl(false);
                //setDatabase(Database.MYSQL);
                setDatabase(Database.valueOf(gwhJpaCoreProperties.getDatabaseType()));
                setDatabasePlatform(gwhJpaCoreProperties.getJpaDialect());
            }
        };
    }

}
