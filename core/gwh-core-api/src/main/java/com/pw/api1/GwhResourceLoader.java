package com.pw.api1;

import java.util.List;

public interface GwhResourceLoader<T> {

    List<T> getAll();

    List<T> getByProfile(String profile);

    List<T> getByProfileAndRegion(String profile, String region);

}
