package com.pw.jdbcroutetemplatesloader1.jdbc.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pw.jdbcroutetemplatesloader1.jdbc.mapper.GwhRouteTemplateRowMapper;
import com.pw.jdbcroutetemplatesloader1.jdbc.model.GwhRouteTemplateModel;

@Repository
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jdbc1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateDao {

    private final String queryAll = "select * from gwh_route_template";
    private final String queryByProfile = "select * from gwh_route_template where profile = ?";

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public GwhRouteTemplateDao(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GwhRouteTemplateModel> getRouteTemplatesByProfile(String profile) {
        return jdbcTemplate.query(queryByProfile, new GwhRouteTemplateRowMapper(), profile);
    }

    public List<GwhRouteTemplateModel> getAllRouteTemplates() {
        return jdbcTemplate.query(queryAll, new GwhRouteTemplateRowMapper());
    }

}
