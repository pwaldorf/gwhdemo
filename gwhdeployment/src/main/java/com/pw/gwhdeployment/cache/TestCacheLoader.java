package com.pw.gwhdeployment.cache;

import java.util.concurrent.atomic.AtomicInteger;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.CacheLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestCacheLoader implements CacheLoader<String, String>{

    AtomicInteger counter = new AtomicInteger(0);

    @Override
    public @Nullable String load(@NonNull String key) throws Exception {
        log.info("load key:{}", key);
        return "testLoader" + counter.incrementAndGet();
    }

}
