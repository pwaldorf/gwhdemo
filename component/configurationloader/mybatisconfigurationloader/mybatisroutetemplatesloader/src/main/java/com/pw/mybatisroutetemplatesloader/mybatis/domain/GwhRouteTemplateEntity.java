package com.pw.mybatisroutetemplatesloader.mybatis.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class GwhRouteTemplateEntity implements Serializable {

    private Long id;
    private String profile;
    private String routeId;
    private String templateParamName;
    private String templateParamValue;

}
