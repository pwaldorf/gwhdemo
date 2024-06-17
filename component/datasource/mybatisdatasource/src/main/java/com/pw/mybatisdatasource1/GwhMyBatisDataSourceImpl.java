package com.pw.mybatisdatasource1;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

//@Configuration
//@ConditionalOnProperty(prefix = "gwh.mybatis.db.datasource", name = "className", matchIfMissing = false)
public class GwhMyBatisDataSourceImpl {

    GwhMyBatisDataSourceProperties gwhMyBatisDataSourceProperties;

    public GwhMyBatisDataSourceImpl(GwhMyBatisDataSourceProperties gwhMyBatisDataSourceProperties) {
        this.gwhMyBatisDataSourceProperties = gwhMyBatisDataSourceProperties;
    }

    @Bean(name = "gwhMyBatisDataSource")
    public DataSource gwhMyBatisDataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(gwhMyBatisDataSourceProperties.getClassName());
        dataSource.setJdbcUrl(gwhMyBatisDataSourceProperties.getUrl());
        dataSource.setUser(gwhMyBatisDataSourceProperties.getUserName());
        dataSource.setPassword(gwhMyBatisDataSourceProperties.getPassword());
        dataSource.setMinPoolSize(gwhMyBatisDataSourceProperties.getMinPoolSize());
        dataSource.setMaxPoolSize(gwhMyBatisDataSourceProperties.getMaxPoolSize());
        dataSource.setAcquireIncrement(gwhMyBatisDataSourceProperties.getIncrement());
        dataSource.setMaxIdleTime(gwhMyBatisDataSourceProperties.getMaxIdleTime());
        dataSource.setAcquireRetryDelay(gwhMyBatisDataSourceProperties.getRetryDelay());
        dataSource.setAcquireRetryAttempts(gwhMyBatisDataSourceProperties.getAcquireRetryAttempts());
        dataSource.setAutoCommitOnClose(gwhMyBatisDataSourceProperties.isAutoCommitOnClose());
        return dataSource;
    }

}
