package com.pw.gwhdatasource1;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ConditionalOnProperty(prefix = "gwh.db.datasource", name = "className", matchIfMissing = false)
public class GwhDataSourceImpl {

    GwhDataSourceProperties gwhDataSourceProperties;

    public GwhDataSourceImpl(GwhDataSourceProperties gwhJdbcDataSourceProperties) {
        this.gwhDataSourceProperties = gwhJdbcDataSourceProperties;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(gwhDataSourceProperties.getClassName());
        dataSource.setJdbcUrl(gwhDataSourceProperties.getUrl());
        dataSource.setUser(gwhDataSourceProperties.getUserName());
        dataSource.setPassword(gwhDataSourceProperties.getPassword());
        dataSource.setMinPoolSize(gwhDataSourceProperties.getMinPoolSize());
        dataSource.setMaxPoolSize(gwhDataSourceProperties.getMaxPoolSize());
        dataSource.setAcquireIncrement(gwhDataSourceProperties.getIncrement());
        dataSource.setMaxIdleTime(gwhDataSourceProperties.getMaxIdleTime());
        dataSource.setAcquireRetryDelay(gwhDataSourceProperties.getRetryDelay());
        dataSource.setAcquireRetryAttempts(gwhDataSourceProperties.getAcquireRetryAttempts());
        dataSource.setAutoCommitOnClose(gwhDataSourceProperties.isAutoCommitOnClose());
        return dataSource;
    }

}
