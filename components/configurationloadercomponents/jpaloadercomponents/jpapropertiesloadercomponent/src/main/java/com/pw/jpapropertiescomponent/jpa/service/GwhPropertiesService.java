package com.pw.jpapropertiescomponent.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pw.jpapropertiescomponent.jpa.model.GwhPropertiesEntity;
import com.pw.jpapropertiescomponent.jpa.repository.GwhPropertiesRepository;

@Service
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
