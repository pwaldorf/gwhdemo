package com.pw.gwhcore.jpa.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "route_template_params")
@ConditionalOnProperty(value = "gtw.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class RouteTemplateEntity {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "route_id")
    private String routeId;    

    @Column(name = "template_param_name")
    private String templateParamName;

    @Column(name = "template_param_value")
    private String templateParamValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "RouteTemplateParams [id=" + id + ", routeId=" + routeId + ", templateParamName=" + templateParamName
                + ", templateParamValue=" + templateParamValue + "]";
    }

}
