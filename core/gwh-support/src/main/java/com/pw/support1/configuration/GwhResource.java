package com.pw.support1.configuration;

import java.util.List;

public interface GwhResource<T> {
    List<T> getResourceAll();

    List<T> getResourceByProfile(String profile);

    List<T> getResourceByProfileAndRegion(String profile, String region);
}
