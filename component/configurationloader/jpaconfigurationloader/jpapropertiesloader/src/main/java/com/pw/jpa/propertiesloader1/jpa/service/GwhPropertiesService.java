package com.pw.jpa.propertiesloader1.jpa.service;

import java.util.ArrayList;
import java.util.List;

import com.pw.jpa.propertiesloader1.jpa.model.GwhPropertiesEntity;
import com.pw.jpa.propertiesloader1.jpa.repository.GwhPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "gwh.framework.load.properties.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhPropertiesService {

    @Autowired
    GwhPropertiesRepository propertiesRepository;

    public List<GwhPropertiesEntity> getAllProperties() {
        List<GwhPropertiesEntity> properties = propertiesRepository.findAll();

        if(properties.size() > 0) {
            return properties;
        } else {
            return new ArrayList<GwhPropertiesEntity>();
        }
    }

    public List<GwhPropertiesEntity> getByProfile(String profile) {
        List<GwhPropertiesEntity> properties = propertiesRepository.findByProfile(profile);

        if(properties.size() > 0) {
            return properties;
        } else {
            return new ArrayList<GwhPropertiesEntity>();
        }
    }

    public List<GwhPropertiesEntity> getByProfileAndRegion(String profile, String region) {
        List<GwhPropertiesEntity> properties = propertiesRepository.findByProfileAndRegion(profile, region);

        if(properties.size() > 0) {
            return properties;
        } else {
            return new ArrayList<GwhPropertiesEntity>();
        }
    }
}
