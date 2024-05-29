package com.pw.gwhcore.gwhcaffeinecache;

import java.util.List;

public interface GwhConfigurationLoader<T> {

    List<T> getAll();

    List<T> getByProfile();

    List<T> getByProfileAndRegion();
}
