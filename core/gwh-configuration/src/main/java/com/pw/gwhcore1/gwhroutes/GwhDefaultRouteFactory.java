package com.pw.gwhcore1.gwhroutes;

import com.pw.api1.configuration.GwhRoute;
import com.pw.api1.configuration.GwhRouteFactory;
import org.springframework.stereotype.Component;

@Component
public class GwhDefaultRouteFactory implements GwhRouteFactory {

    @Override
    public GwhRoute createRoute(String routeId,
                                String routeType,
                                String route) {
        return new GwhDefaultRoute(routeId, routeType, route);
    }

}
