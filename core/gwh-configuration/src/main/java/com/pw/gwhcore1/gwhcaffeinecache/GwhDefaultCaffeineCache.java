package com.pw.gwhcore1.gwhcaffeinecache;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pw.api1.configuration.GwhCaffeineCache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Component
@Scope("prototype")
@Data
@AllArgsConstructor
@Builder
public class GwhDefaultCaffeineCache implements GwhCaffeineCache {

    String name;
    Integer initialCapacity;
    Integer maximumSize;
    String evictionType;
    Integer expireAfterAccessTime;
    Integer expireAfterWriteTime;
    Boolean statsEnabled;
    String statsCounter;
    String cacheLoader;

}
