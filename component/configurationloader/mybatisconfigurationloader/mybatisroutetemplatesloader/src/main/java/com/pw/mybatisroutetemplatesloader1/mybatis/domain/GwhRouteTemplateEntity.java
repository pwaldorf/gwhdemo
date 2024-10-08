package com.pw.mybatisroutetemplatesloader1.mybatis.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class GwhRouteTemplateEntity implements Serializable {

    private Long id;
    private String profile;
    private String region;
    private String version;
    private String routeId;
    private String templateParamName;
    private String templateParamValue;

}
