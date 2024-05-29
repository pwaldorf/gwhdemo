package com.pw.support.route;

import com.pw.support.model.GwhRoutePolicies;

public class DefaultRoutePolicy implements DefaultRoutePolicies {

    @Override
    public GwhRoutePolicies getRoutePolicies() {
        return new GwhRoutePolicies();
    }

}
