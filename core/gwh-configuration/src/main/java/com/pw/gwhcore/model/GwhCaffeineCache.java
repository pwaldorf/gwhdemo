package com.pw.gwhcore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GwhCaffeineCache {

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
