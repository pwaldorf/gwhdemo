package com.pw.jpadatasource1;

import java.util.Map;
import java.util.Properties;

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


//@Configuration
//@EnableTransactionManagement
////@EnableJpaRepositories(basePackages = {"com.pw.jpaproperties1.jpa.repository",
//////                                       "com.pw.jparoutesloader1.jpa.repository",
//////                                       "com.pw.jparoutetemplatesloader1.jpa.repository",
//////                                       "com.pw.jpacaffeinecachesloader1.jpa.repository",
////                                       "com.pw.gwhcore.jpa1.repository"},
////                       entityManagerFactoryRef = "gwhEntityManagerFactory",
////                       transactionManagerRef = "gwhTransactionManager")
//@ConditionalOnProperty(prefix = "gwh.jpa.db.datasource", name = "className", matchIfMissing = false)
public class GwhJpaConfiguration {

    private GwhJpaDataSourceProperties gwhJpaDataSourceProperties;

    private GwhJpaDataSourceConfiguration gwhJpaDataSourceConfiguration;

    public GwhJpaConfiguration(GwhJpaDataSourceProperties gwhJpaDataSourceProperties,
                               GwhJpaDataSourceConfiguration gwhJpaDataSourceConfiguration) {
        this.gwhJpaDataSourceProperties = gwhJpaDataSourceProperties;
        this.gwhJpaDataSourceConfiguration = gwhJpaDataSourceConfiguration;
    }

    @Bean(name = "gwhEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(gwhJpaDataSourceConfiguration.getDataSource());
        sessionFactory.setPackagesToScan(new String[] {"com.pw.jpa.*"});
        //        sessionFactory.setPackagesToScan(new String[] {"com.pw.jpapropertiesloader1.jpa.model",
//                                                       "com.pw.jparoutesloader1.jpa.model",
//                                                       "com.pw.jparoutetemplatesloader1.jpa.model",
//                                                       "com.pw.jpacaffeinecachesloader1.jpa.model",
//                                                       "com.pw.gwhcore.jpa1.model"});
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