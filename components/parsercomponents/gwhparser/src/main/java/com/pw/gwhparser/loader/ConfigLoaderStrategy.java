package com.pw.gwhparser.loader;

public interface ConfigLoaderStrategy<T, U> {

    public void load(T object, U data);

}
