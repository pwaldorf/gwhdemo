package com.pw.jpa.caffeinecacheloader1.jpa.service;

import java.util.ArrayList;
import java.util.List;

import com.pw.jpa.caffeinecacheloader1.jpa.model.GwhCaffeineCacheEntity;
import com.pw.jpa.caffeinecacheloader1.jpa.repository.GwhCaffeineCacheRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "gwh.framework.load.caches.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhCaffeineCacheService {

    private final GwhCaffeineCacheRepository caffeineCacheRepository;

    public GwhCaffeineCacheService(GwhCaffeineCacheRepository caffeineCacheRepository) {
        this.caffeineCacheRepository = caffeineCacheRepository;
    }

    public List<GwhCaffeineCacheEntity> getAll() {
        List<GwhCaffeineCacheEntity> caffeineCaches = caffeineCacheRepository.findAll();

        if(CollectionUtils.isNotEmpty(caffeineCaches)) {
            return caffeineCaches;
        } else {
            return new ArrayList<GwhCaffeineCacheEntity>();
        }
    }

    public List<GwhCaffeineCacheEntity> getByProfile(String profile) {
        List<GwhCaffeineCacheEntity> caffeineCaches = caffeineCacheRepository.findByProfile(profile);

        if(CollectionUtils.isNotEmpty(caffeineCaches)) {
            return caffeineCaches;
        } else {
            return new ArrayList<GwhCaffeineCacheEntity>();
        }
    }

    public List<GwhCaffeineCacheEntity> getByProfileAndRegion(String profile, String region) {
        List<GwhCaffeineCacheEntity> caffeineCaches = caffeineCacheRepository.findByProfileAndRegion(profile, region);

        if(CollectionUtils.isNotEmpty(caffeineCaches)) {
            return caffeineCaches;
        } else {
            return new ArrayList<GwhCaffeineCacheEntity>();
        }
    }
}
