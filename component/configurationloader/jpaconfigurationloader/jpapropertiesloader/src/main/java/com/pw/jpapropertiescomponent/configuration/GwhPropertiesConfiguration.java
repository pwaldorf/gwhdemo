package com.pw.jpapropertiescomponent.configuration;

import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.pw.gwhcore.configurations.GwhCoreProperties;
import com.pw.gwhcore.gwhproperties.GwhProperties;
import com.pw.jpapropertiescomponent.jpa.model.GwhPropertiesEntity;
import com.pw.jpapropertiescomponent.jpa.service.GwhPropertiesService;

@Component
public class GwhPropertiesConfiguration implements GwhProperties{

    private GwhCoreProperties gwhCoreProperties;
    private GwhPropertiesService propertiesService;

    public GwhPropertiesConfiguration(GwhCoreProperties gwhCoreProperties, GwhPropertiesService propertiesService) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.propertiesService = propertiesService;
    }

    @Override
    public Properties getProperties() {

        Properties properties = new Properties();
        gwhPropertiesEntities().forEach(item -> properties.setProperty(item.getPropertyKey(), item.getPropertyValue()));
        return properties;
    }

    public List<GwhPropertiesEntity> gwhPropertiesEntities() {
        return propertiesService.getByProfile(gwhCoreProperties.getProfile());
    }
}
