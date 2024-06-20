package com.pw.support1.route;

import com.pw.support1.model.GwhRoutePolicies;
import com.pw.support1.util.ApplicationContextProvider;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.context.ApplicationContext;

public abstract class GwhAbstractRouteTemplate extends EndpointRouteBuilder {

    public GwhEndpointConsumerBuilder getConsumerEndpointRouteBuilderByName(String name) {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        return ctx.getBean(name, GwhEndpointConsumerBuilder.class);
    }

    public GwhEndpointProducerBuilder getProducerEndpointRouteBuilderByName(String name) {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        return ctx.getBean(name, GwhEndpointProducerBuilder.class);
    }

    public <S extends GwhRoutePolicyBuilder> GwhRoutePolicies getRoutePolicies(Class<S> clazz) {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        GwhRoutePolicies routePolicies = new GwhRoutePolicies();
        ctx.getBeansOfType(clazz).forEach((beanName, bean) -> {
            routePolicies.addRoutePolicies(bean.getRoutePolicies());
        });
        return routePolicies;
    }
}
