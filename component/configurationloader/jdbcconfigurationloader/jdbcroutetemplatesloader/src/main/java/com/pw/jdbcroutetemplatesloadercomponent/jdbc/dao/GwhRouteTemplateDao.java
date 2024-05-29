package com.pw.jdbcroutetemplatesloadercomponent.jdbc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pw.jdbcroutetemplatesloadercomponent.jdbc.mapper.GwhRouteTemplateRowMapper;
import com.pw.jdbcroutetemplatesloadercomponent.jdbc.model.GwhRouteTemplateModel;
import com.pw.support.datasource.GwhDataSource;

@Repository
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jdbc.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateDao {

    private final String queryAll = "select * from gwh_route_template";
    private final String queryByProfile = "select * from gwh_route_template where profile = ?";

    private final JdbcTemplate jdbcTemplate;
    private final GwhDataSource gwhDataSource;

    public GwhRouteTemplateDao(@Qualifier("jdbcDataSource") GwhDataSource gwhDataSource) {
        this.gwhDataSource = gwhDataSource;
        jdbcTemplate = new JdbcTemplate(this.gwhDataSource.getDataSource());
    }

    public List<GwhRouteTemplateModel> getRouteTemplatesByProfile(String profile) {
        return jdbcTemplate.query(queryByProfile, new GwhRouteTemplateRowMapper(), profile);
    }

    public List<GwhRouteTemplateModel> getAllRouteTemplates() {
        return jdbcTemplate.query(queryAll, new GwhRouteTemplateRowMapper());
    }

}
