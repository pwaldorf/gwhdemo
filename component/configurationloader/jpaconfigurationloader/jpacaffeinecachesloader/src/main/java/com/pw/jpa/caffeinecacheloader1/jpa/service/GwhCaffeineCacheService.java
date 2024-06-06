package com.pw.jpa.caffeinecacheloader1.jpa.service;

import java.util.ArrayList;
import java.util.List;

import com.pw.jpa.caffeinecacheloader1.jpa.model.GwhCaffeineCacheEntity;
import com.pw.jpa.caffeinecacheloader1.jpa.repository.GwhCaffeineCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnProperty(value = "gwh.framework.load.caches.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhCaffeineCacheService {

    @Autowired
    GwhCaffeineCacheRepository caffeineCacheRepository;

    public List<GwhCaffeineCacheEntity> getAll() {
        List<GwhCaffeineCacheEntity> caffeineCaches = caffeineCacheRepository.findAll();

        if(caffeineCaches.size() > 0) {
            return caffeineCaches;
        } else {
            return new ArrayList<GwhCaffeineCacheEntity>();
        }
    }

    public List<GwhCaffeineCacheEntity> getByProfile(String profile) {
        List<GwhCaffeineCacheEntity> caffeineCaches = caffeineCacheRepository.findByProfile(profile);

        if(caffeineCaches.size() > 0) {
            return caffeineCaches;
        } else {
            return new ArrayList<GwhCaffeineCacheEntity>();
        }
    }
}
