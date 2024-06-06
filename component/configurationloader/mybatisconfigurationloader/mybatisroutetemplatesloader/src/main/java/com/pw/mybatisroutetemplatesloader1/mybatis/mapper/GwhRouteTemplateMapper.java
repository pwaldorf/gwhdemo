package com.pw.mybatisroutetemplatesloader1.mybatis.mapper;

import java.util.List;

import com.pw.mybatisroutetemplatesloader1.mybatis.domain.GwhRouteTemplateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GwhRouteTemplateMapper {

    @Select("select id, profile, route_id, template_param_name, template_param_value from route_template_params")
    List<GwhRouteTemplateEntity> getAllRouteTemplates();

    @Select("select id, profile, route_id, template_param_name, template_param_value from route_template_params where profile = #{profile}")
    List<GwhRouteTemplateEntity> getByProfile(@Param("profile") String profile);

}
