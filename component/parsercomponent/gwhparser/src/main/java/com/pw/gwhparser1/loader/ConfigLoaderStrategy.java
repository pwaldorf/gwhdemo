package com.pw.gwhparser1.loader;

public interface ConfigLoaderStrategy<T, U> {

    public void load(T object, U data);

}
