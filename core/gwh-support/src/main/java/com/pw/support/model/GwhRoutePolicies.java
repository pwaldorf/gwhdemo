package com.pw.support.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.spi.RoutePolicy;

public class GwhRoutePolicies {

    List<RoutePolicy> routePolicies;

    public List<RoutePolicy> getRoutePolicies() {
        if (routePolicies == null) {
            routePolicies = new ArrayList<>();
        }
        return routePolicies;
    }

    public void setRoutePolicies(List<RoutePolicy> routePolicies) {
        this.routePolicies = routePolicies;
    }

    public void addRoutePolicies(RoutePolicy routePolicy) {
        if (routePolicies == null) {
            routePolicies = new ArrayList<>();
        }
        routePolicies.add(routePolicy);
    }

    public RoutePolicy[] toArrayRoutePolicies() {
        if (routePolicies == null) {
            routePolicies = new ArrayList<>();
        }
        return routePolicies.toArray(RoutePolicy[]::new);
    }
}
