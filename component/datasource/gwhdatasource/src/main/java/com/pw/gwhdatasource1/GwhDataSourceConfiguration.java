package com.pw.gwhdatasource1;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.support1.datasource.GwhDataSource;

@Component
@ConditionalOnProperty(prefix = "gwh.db.datasource", name = "className", matchIfMissing = false)
public class GwhDataSourceConfiguration implements GwhDataSource {

    DataSource dataSource;

    public GwhDataSourceConfiguration(@Qualifier("gwhDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
