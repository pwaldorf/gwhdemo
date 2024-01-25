package com.pw.gwhcore.configs;

import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@ConditionalOnProperty(value = "gtw.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class JpaConfigs {

    @Value("${db.datasource.className}")
    String className;

    @Value("${db.datasource.url}")
    String url;

    @Value("${db.datasource.userName}")
    String userName;

    @Value("${db.datasource.password}")
    String password;
    
    @Value("${db.datasource.minpoolsize:0}")
    Integer minPoolSize;

    @Value("${db.datasource.maxpoolsize:20}")
    Integer maxPoolSize;

    @Value("${db.datasource.increment:2}")
    Integer increment;

    @Value("${db.datasource.maxidleTime:300}")
    Integer maxIdleTime;

    @Value("${db.datasource.retryDelay:600000}")
    Integer retryDelay;

    @Value("${db.jpa.hibernate.ddl-auto:validate}")
    String ddlAuto;

    @Value("${db.jpa.hibernate.dialect:org.hibernate.dialect.MySQLDialect}")    
    String dialect;

    @Value("${db.jpa.hibernate.naming.physical-strategy:org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy}")
    String physicalNamingStrategy;


    @Bean(name = "dataSource")
    public DataSource dataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(className);        
        dataSource.setJdbcUrl(url);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setAcquireIncrement(increment);
        dataSource.setMaxIdleTime(maxIdleTime);
        dataSource.setAcquireRetryDelay(retryDelay);
        dataSource.setAcquireRetryAttempts(Integer.MAX_VALUE);
        dataSource.setAutoCommitOnClose(false);
        return dataSource;
    }
    
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException{
        final LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] {"com.pw.gwhcore.jpa.model"});        
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        sessionFactory.setJpaVendorAdapter(vendorAdapter);
        sessionFactory.setJpaProperties(hibernateProperties());        
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;        
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();        
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", ddlAuto);
                setProperty("hibernate.dialect", dialect);
                setProperty("hibernate.physical_naming_strategy", physicalNamingStrategy);
            }
        };
    }
     
}
