package com.pw.datafiledataformats.loaders;

public interface GwhDataLoader<T> {

    T load(String formatName);

}
