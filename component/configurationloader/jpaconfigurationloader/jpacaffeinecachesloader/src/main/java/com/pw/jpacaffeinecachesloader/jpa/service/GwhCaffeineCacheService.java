package com.pw.jpacaffeinecachesloader.jpa.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.pw.jpacaffeinecachesloader.jpa.model.GwhCaffeineCacheEntity;
import com.pw.jpacaffeinecachesloader.jpa.repository.GwhCaffeineCacheRepository;


@Service
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa.enabled", havingValue = "true", matchIfMissing = false)
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
