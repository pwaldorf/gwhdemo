package com.pw.support1.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public abstract class GwhAbstractRouteTemplate<T> extends EndpointRouteBuilder {

    private final T endpointRouteBuilder;

    public GwhAbstractRouteTemplate(T endpointRouteBuilder) {
        this.endpointRouteBuilder = endpointRouteBuilder;
    }

    public T getEndpointRouteBuilder() {
        return endpointRouteBuilder;
    }

}