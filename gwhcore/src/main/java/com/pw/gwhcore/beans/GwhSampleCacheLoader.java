package com.pw.gwhcore.beans;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.CacheLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class GwhSampleCacheLoader implements CacheLoader<String, String> {

    @Override
    public @Nullable String load(String key) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

    @Override
    public Map<? extends String, ? extends String> loadAll(Set<? extends String> keys) throws Exception {
        var result = new HashMap<String, String>(keys.size());
        for (String key : keys) {
            result.put(key, this.load(key));
        }
        return result;
    }


}
