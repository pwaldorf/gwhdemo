package com.pw.mybatisdatasource.configuration;

import com.pw.support.datasource.GwhDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@ConditionalOnProperty(prefix = "gwh.mybatis.db.datasource", name = "className", matchIfMissing = false)
public class GwhMyBatisDataSourceConfiguration implements GwhDataSource {

    DataSource dataSource;

    public GwhMyBatisDataSourceConfiguration(@Qualifier("gwhMyBatisDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
