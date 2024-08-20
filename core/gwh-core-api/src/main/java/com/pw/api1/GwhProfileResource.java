package com.pw.api1;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public interface GwhProfileResource<T> extends GwhResource {

    default List<T> getResource(String profile, String region, String version) {

        if (isEmptyOrAll(profile)) {
            return getResourceAll();
        }
        if (isEmptyOrAll(region)) {
            return getResourceByProfile(profile);
        }
        return getResourceByProfileAndRegionAndVersion(profile, region, version);

    }

    default boolean isEmptyOrAll(String value) {
        return StringUtils.isEmpty(value) || "ALL".equalsIgnoreCase(value);
    }

    List<T> getResourceAll();

    List<T> getResourceByProfile(String profile);

    List<T> getResourceByProfileAndRegionAndVersion(String profile, String region, String version);
}
