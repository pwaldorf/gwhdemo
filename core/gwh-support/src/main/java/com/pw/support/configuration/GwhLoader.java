package com.pw.support.configuration;

import java.util.List;

public interface GwhLoader<T> {

    List<T> getAll();

    List<T> getByProfile();

    List<T> getByProfileAndRegion();
}
