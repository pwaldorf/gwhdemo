package com.pw.gwhcore1.gwhcaffeinecache;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.github.benmanes.caffeine.cache.CacheLoader;

//@Component("GwhSampleCacheLoader")
public class GwhSampleCacheLoader implements CacheLoader<String, String> {

    @Override
    public @Nullable String load(@NonNull String arg0) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

    // @Override
    // public @Nullable String load(String key) throws Exception {
    //     //Implement single record retrieval
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'load'");
    // }

    // @Override
    // public Map<? extends String, ? extends String> loadAll(Set<? extends String> keys) throws Exception {
    //     var result = new HashMap<String, String>(keys.size());
    //     for (String key : keys) {
    //         result.put(key, this.load(key));
    //     }
    //     return result;
    // }


}
