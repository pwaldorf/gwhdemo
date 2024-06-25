package com.pw.jpa.routepropertiesloader1.jpa.service;

import java.util.ArrayList;
import java.util.List;

import com.pw.jpa.routepropertiesloader1.jpa.model.GwhRoutePropertiesEntity;
import com.pw.jpa.routepropertiesloader1.jpa.repository.GwhRoutePropertiesRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "gwh.framework.load.route.properties.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRoutePropertiesService {

    private final GwhRoutePropertiesRepository propertiesRepository;

    public GwhRoutePropertiesService(GwhRoutePropertiesRepository propertiesRepository) {
        this.propertiesRepository = propertiesRepository;
    }

    public List<GwhRoutePropertiesEntity> getAllProperties() {
        List<GwhRoutePropertiesEntity> properties = propertiesRepository.findAll();

        if(CollectionUtils.isNotEmpty(properties)) {
            return properties;
        } else {
            return new ArrayList<GwhRoutePropertiesEntity>();
        }
    }

    public List<GwhRoutePropertiesEntity> getByProfile(String profile) {
        List<GwhRoutePropertiesEntity> properties = propertiesRepository.findByProfile(profile);

        if(CollectionUtils.isNotEmpty(properties)) {
            return properties;
        } else {
            return new ArrayList<GwhRoutePropertiesEntity>();
        }
    }

    public List<GwhRoutePropertiesEntity> getByProfileAndRegion(String profile, String region) {
        List<GwhRoutePropertiesEntity> properties = propertiesRepository.findByProfileAndRegion(profile, region);

        if(CollectionUtils.isNotEmpty(properties)) {
            return properties;
        } else {
            return new ArrayList<GwhRoutePropertiesEntity>();
        }
    }
}
