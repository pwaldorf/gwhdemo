package com.pw.support1.route;

import com.pw.support1.model.GwhRoutePolicies;
import org.apache.camel.spi.RoutePolicy;

public interface GwhRoutePolicyBuilder {

    RoutePolicy getRoutePolicies();
}
