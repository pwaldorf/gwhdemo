package com.pw.support.configuration;

import java.util.List;

public abstract class GwhResourceLoader<T> implements GwhLoader<T> {

    private final GwhCoreProperties gwhCoreProperties;
    private final GwhResource<T> gwhResource;

    public GwhResourceLoader(GwhCoreProperties gwhCoreProperties, GwhResource<T> gwhResource) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.gwhResource = gwhResource;
    }

    @Override
    public List<T> getAll() {
        return gwhResource.getResourceAll();
    }

    @Override
    public List<T> getByProfile() {
        return gwhResource.getResourceByProfile(gwhCoreProperties.getProfile());
    }

    @Override
    public List<T> getByProfileAndRegion() {
        return null;
    }

}