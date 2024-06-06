package com.pw.jpa.propertiesloader1.configuration;

import com.pw.gwhcore1.gwhproperties.GwhPropertiesResource;
import com.pw.gwhcore1.model.GwhProperty;
import com.pw.jpa.propertiesloader1.jpa.service.GwhPropertiesService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.properties.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class JpaPropertiesConfiguration implements GwhPropertiesResource {

    private final GwhPropertiesService propertiesService;

    public JpaPropertiesConfiguration(GwhPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @Override
    public List<GwhProperty> getResourceAll() {
        return propertiesService.getAllProperties()
                .stream()
                .map(item -> new GwhProperty(item.getPropertyKey(), item.getPropertyValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhProperty> getResourceByProfile(String profile) {
        return null;
    }

    @Override
    public List<GwhProperty> getResourceByProfileAndRegion(String profile, String region) {
        return null;
    }
}
