package com.pw.jpadatasource1;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//@Configuration
//@ConditionalOnProperty(prefix = "gwh.jpa.db.datasource", name = "className", matchIfMissing = false)
public class GwhJpaDataSourceImpl {

    private final GwhJpaDataSourceProperties gwhJpaDataSourceProperties;

    public GwhJpaDataSourceImpl(GwhJpaDataSourceProperties gwhJpaDataSourceProperties) {
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

}
