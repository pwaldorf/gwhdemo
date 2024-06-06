package com.pw.jdbcroutetemplatesloader1.jdbc.model;

public class GwhRouteTemplateModel {

    private Long id;
    private String profile;
    private String routeId;
    private String templateParamName;
    private String templateParamValue;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(final String profile) {
        this.profile = profile;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(final String routeId) {
        this.routeId = routeId;
    }

    public String getTemplateParamName() {
        return templateParamName;
    }

    public void setTemplateParamName(final String templateParamName) {
        this.templateParamName = templateParamName;
    }

    public String getTemplateParamValue() {
        return templateParamValue;
    }

    public void setTemplateParamValue(final String templateParamValue) {
        this.templateParamValue = templateParamValue;
    }

}
