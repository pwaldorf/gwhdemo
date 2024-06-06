package com.pw.jdbcroutetemplatesloader1.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pw.jdbcroutetemplatesloader1.jdbc.model.GwhRouteTemplateModel;

public class GwhRouteTemplateRowMapper implements RowMapper<GwhRouteTemplateModel> {

    @Override
    public GwhRouteTemplateModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final GwhRouteTemplateModel gwhRouteTemplateModel = new GwhRouteTemplateModel();
        gwhRouteTemplateModel.setId(rs.getLong("id"));
        gwhRouteTemplateModel.setProfile(rs.getString("profile"));
        gwhRouteTemplateModel.setRouteId(rs.getString("route_id"));
        gwhRouteTemplateModel.setTemplateParamName(rs.getString("template_param_name"));
        gwhRouteTemplateModel.setTemplateParamValue(rs.getString("template_param_value"));
        return gwhRouteTemplateModel;
    }

}
