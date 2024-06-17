package com.pw.gwhcore1.gwhrouteproperties;

import com.pw.api1.configuration.GwhRouteProperty;

public class GwhDefaultRouteProperty implements GwhRouteProperty {

    private String routeId;
    private String key;
    private String value;

    public GwhDefaultRouteProperty(String routeId, String key, String value) {
        this.routeId = routeId;
        this.key = key;
        this.value = value;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
