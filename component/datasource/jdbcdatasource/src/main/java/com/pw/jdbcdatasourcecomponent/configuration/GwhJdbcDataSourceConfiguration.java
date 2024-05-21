package com.pw.jdbcdatasourcecomponent.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.support.datasource.GwhDataSource;

@Component
@ConditionalOnProperty(prefix = "gwh.jpa.db.datasource", name = "className", matchIfMissing = false)
public class GwhJdbcDataSourceConfiguration implements GwhDataSource {

    DataSource dataSource;

    public GwhJdbcDataSourceConfiguration(@Qualifier("gwhJdbcDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
