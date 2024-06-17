package com.pw.gwhcore1.gwhroutetemplates;


import com.pw.api1.configuration.GwhRouteTemplate;

public class GwhDefaultRouteTemplate implements GwhRouteTemplate {

    private String routeId;
    private String templateParamName;
    private String templateParamValue;

//    public static GwhRouteTemplate createRouteTemplate(String routeId,
//                                                       String templateParamName,
//                                                       String templateParamValue) {
//        return new GwhDefaultRouteTemplate(routeId, templateParamName, templateParamValue);
//    }

    public GwhDefaultRouteTemplate(String routeId, String templateParamName, String templateParamValue) {
        this.routeId = routeId;
        this.templateParamName = templateParamName;
        this.templateParamValue = templateParamValue;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getTemplateParamName() {
        return templateParamName;
    }

    public void setTemplateParamName(String templateParamName) {
        this.templateParamName = templateParamName;
    }

    public String getTemplateParamValue() {
        return templateParamValue;
    }

    public void setTemplateParamValue(String templateParamValue) {
        this.templateParamValue = templateParamValue;
    }
}
