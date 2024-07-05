package com.pw.support1.route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.spi.RoutePolicy;

import com.pw.support1.annotation.GwhRoutePolicy;
import com.pw.support1.util.ApplicationContextProvider;

public interface GwhEndpointRouteBuilderExtension {

    default GwhEndpointConsumerBuilder getConsumerEndpointRouteBuilderByName(String name) {
        return ApplicationContextProvider.getApplicationContext()
            .getBean(name, GwhEndpointConsumerBuilder.class);
    }

    default GwhEndpointProducerBuilder getProducerEndpointRouteBuilderByName(String name) {
        return ApplicationContextProvider.getApplicationContext()
            .getBean(name, GwhEndpointProducerBuilder.class);
    }

    default <S extends GwhRoutePolicyBuilder> RoutePolicy[] getRoutePoliciesByInterface(Class<S> clazz) {

        List<RoutePolicy> routePolicies = new ArrayList<>();
        ApplicationContextProvider.getApplicationContext().getBeansOfType(clazz).forEach((beanName, bean) -> {
            routePolicies.add(bean.getRoutePolicies());
        });
        return routePolicies.toArray(RoutePolicy[]::new);
    }

    default RoutePolicy[] getRoutePoliciesByAnnotation(String componentName) {

        List<RoutePolicy> routePolicies = new ArrayList<>();
        Class<GwhRoutePolicy> annotation = GwhRoutePolicy.class;
        ApplicationContextProvider.getApplicationContext().getBeansWithAnnotation(annotation).forEach((beanName, bean) -> {

            if ((Arrays.stream(bean.getClass().getAnnotation(annotation).values()).anyMatch(componentName::equals))
                || (Arrays.stream(bean.getClass().getAnnotation(annotation).values()).anyMatch("default"::equals))) {
                if (bean instanceof GwhRoutePolicyBuilder) {
                    routePolicies.add(((GwhRoutePolicyBuilder)bean).getRoutePolicies());
                }
            }
        });
        return routePolicies.toArray(RoutePolicy[]::new);

    }
}
