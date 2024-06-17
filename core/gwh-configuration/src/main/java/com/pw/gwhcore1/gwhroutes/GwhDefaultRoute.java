package com.pw.gwhcore1.gwhroutes;

import com.pw.api1.configuration.GwhRoute;

public class GwhDefaultRoute implements GwhRoute {

    private String routeId;
    private String route;

    public GwhDefaultRoute(String routeId, String route) {
        this.routeId = routeId;
        this.route = route;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
