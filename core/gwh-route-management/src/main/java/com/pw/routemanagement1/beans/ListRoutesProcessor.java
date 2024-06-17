package com.pw.routemanagement1.beans;

import com.pw.routemanagement1.model.GwhListRoute;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@ConditionalOnProperty(value = "gwh.framework.core.routemanagement.enabled", havingValue = "true", matchIfMissing = false)
public class ListRoutesProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        
        List<Route> routes = exchange.getContext().getRoutes();
        GwhListRoute gwhListRoute = exchange.getIn().getBody(GwhListRoute.class);

        List<String> routeNames = routes.stream()
                .filter(route -> StringUtils.isEmpty(gwhListRoute.getRouteGroup()) ||
                        (route.getGroup().equalsIgnoreCase(gwhListRoute.getRouteGroup())))
                .filter(route -> StringUtils.isEmpty(gwhListRoute.getRouteId()) ||
                        (route.getRouteId().equalsIgnoreCase(gwhListRoute.getRouteId())))
                .filter(route -> StringUtils.isEmpty(gwhListRoute.getRouteType()) ||
                        (route.getProperties().get("RouteType").toString().equalsIgnoreCase(gwhListRoute.getRouteType())))
                .filter(route -> StringUtils.isEmpty(gwhListRoute.getRouteService()) ||
                        (route.getProperties().get("RouteService").toString().equalsIgnoreCase(gwhListRoute.getRouteService())))
                .filter(route -> StringUtils.isEmpty(gwhListRoute.getRouteEndpoint()) ||
                        (route.getProperties().get("RouteEndpoint").toString().equalsIgnoreCase(gwhListRoute.getRouteEndpoint())))
                .map(Route::getId)
                .collect(Collectors.toList());
        exchange.getIn().setBody(routeNames.toString());
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/plain");        
        
        log.info("ListRoutesProcessor: routeIds: {} ", routeNames.toString());
        
    }
    
}
