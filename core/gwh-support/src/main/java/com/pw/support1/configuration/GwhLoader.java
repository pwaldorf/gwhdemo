package com.pw.support1.configuration;

import java.util.List;

public interface GwhLoader<T> {

    List<T> getAll();

    List<T> getByProfile();

    List<T> getByProfileAndRegion();
}
