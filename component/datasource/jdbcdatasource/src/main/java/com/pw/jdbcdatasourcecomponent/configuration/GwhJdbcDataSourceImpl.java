package com.pw.jdbcdatasourcecomponent.configuration;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ConditionalOnProperty(prefix = "gwh.jpa.db.datasource", name = "className", matchIfMissing = false)
public class GwhJdbcDataSourceImpl {

    GwhJdbcDataSourceProperties gwhJdbcDataSourceProperties;

    public GwhJdbcDataSourceImpl(GwhJdbcDataSourceProperties gwhJdbcDataSourceProperties) {
        this.gwhJdbcDataSourceProperties = gwhJdbcDataSourceProperties;
    }

    @Bean(name = "gwhJdbcDataSource")
    public DataSource gwhJdbcDataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(gwhJdbcDataSourceProperties.getClassName());
        dataSource.setJdbcUrl(gwhJdbcDataSourceProperties.getUrl());
        dataSource.setUser(gwhJdbcDataSourceProperties.getUserName());
        dataSource.setPassword(gwhJdbcDataSourceProperties.getPassword());
        dataSource.setMinPoolSize(gwhJdbcDataSourceProperties.getMinPoolSize());
        dataSource.setMaxPoolSize(gwhJdbcDataSourceProperties.getMaxPoolSize());
        dataSource.setAcquireIncrement(gwhJdbcDataSourceProperties.getIncrement());
        dataSource.setMaxIdleTime(gwhJdbcDataSourceProperties.getMaxIdleTime());
        dataSource.setAcquireRetryDelay(gwhJdbcDataSourceProperties.getRetryDelay());
        dataSource.setAcquireRetryAttempts(gwhJdbcDataSourceProperties.getAcquireRetryAttempts());
        dataSource.setAutoCommitOnClose(gwhJdbcDataSourceProperties.isAutoCommitOnClose());
        return dataSource;
    }

}
