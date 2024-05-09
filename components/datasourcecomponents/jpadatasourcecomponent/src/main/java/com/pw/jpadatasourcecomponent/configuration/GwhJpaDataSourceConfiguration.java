package com.pw.jpadatasourcecomponent.configuration;

import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class GwhJpaDataSourceConfiguration {

    GwhJpaDataSourceProperties gwhJpaDataSourceProperties;

    public GwhJpaDataSourceConfiguration(GwhJpaDataSourceProperties gwhJpaDataSourceProperties) {
        this.gwhJpaDataSourceProperties = gwhJpaDataSourceProperties;
    }

    @Bean(name = "gwhJpaDataSource")
    public DataSource gwhJpaDataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(gwhJpaDataSourceProperties.getClassName());
        dataSource.setJdbcUrl(gwhJpaDataSourceProperties.getUrl());
        dataSource.setUser(gwhJpaDataSourceProperties.getUserName());
        dataSource.setPassword(gwhJpaDataSourceProperties.getPassword());
        dataSource.setMinPoolSize(gwhJpaDataSourceProperties.getMinPoolSize());
        dataSource.setMaxPoolSize(gwhJpaDataSourceProperties.getMaxPoolSize());
        dataSource.setAcquireIncrement(gwhJpaDataSourceProperties.getIncrement());
        dataSource.setMaxIdleTime(gwhJpaDataSourceProperties.getMaxIdleTime());
        dataSource.setAcquireRetryDelay(gwhJpaDataSourceProperties.getRetryDelay());
        dataSource.setAcquireRetryAttempts(gwhJpaDataSourceProperties.getAcquireRetryAttempts());
        dataSource.setAutoCommitOnClose(gwhJpaDataSourceProperties.isAutoCommitOnClose());
        return dataSource;
    }

    @Bean(name = "gwhJpaProperties")
    public Properties gwhJpaProperties() {
        Map<String, String> mapProperties = gwhJpaDataSourceProperties.getJpaProperties();
        Properties properties = new Properties();

        mapProperties.forEach((k,v) -> properties.setProperty(k, v));
        return properties;
    }

    @Bean(name = "gwhJpaVendorAdapter")
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
