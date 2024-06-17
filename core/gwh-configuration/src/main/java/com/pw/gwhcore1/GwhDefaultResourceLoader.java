package com.pw.gwhcore1;

import com.pw.api1.GwhResource;
import com.pw.api1.GwhResourceLoader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.List;

public class GwhDefaultResourceLoader<T> implements GwhResourceLoader<T> {

    private final GwhResource<T> gwhResource;

    public GwhDefaultResourceLoader(@Nullable GwhResource<T> gwhResource) {
        this.gwhResource = gwhResource;
    }

    public List<T> getResource(GwhConfigurationProperties gwhConfigurationProperties) {
        String profile = gwhConfigurationProperties.getProfile();
        String region = gwhConfigurationProperties.getRegion();

        if (StringUtils.isEmpty(profile) || "ALL".equalsIgnoreCase(profile)) {
            return getAll();
        }
        if (StringUtils.isEmpty(region) || "ALL".equalsIgnoreCase(region)) {
            return getByProfile(profile);
        }
        return getByProfileAndRegion(profile, region);

    }

    @Override
    public List<T> getAll() {
        return gwhResource != null ? gwhResource.getResourceAll() : List.of();
    }

    @Override
    public List<T> getByProfile(String profile) {
        return gwhResource != null ? gwhResource.getResourceByProfile(profile) : List.of();
    }

    @Override
    public List<T> getByProfileAndRegion(String profile, String region) {
        return gwhResource != null ? gwhResource.getResourceByProfileAndRegion(profile, region) : List.of();
    }

}