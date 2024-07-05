package com.pw.jpa.dataformatloader1.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pw.api1.GwhResource;
import com.pw.dataformat1.dataformat.dataformat.GwhDataFormatConfiguration;
import com.pw.jpa.dataformatloader1.jpa.service.GwhDataFormatService;

@Component
public class JpaDataFormatResource implements GwhResource<GwhDataFormatConfiguration> {

    private final GwhDataFormatService gwhDataFormatService;

    public JpaDataFormatResource(GwhDataFormatService gwhDataFormatService) {
        this.gwhDataFormatService = gwhDataFormatService;
    }

    @Override
    public List<GwhDataFormatConfiguration> getResourceAll() {
        return gwhDataFormatService.getAllDataFormats()
             .stream()
             .map(item -> new GwhDataFormatConfiguration(item.getDataFormatName(),
                item.getDataFormatConfig(),
                item.getDataFormatDefinition()))
             .collect(Collectors.toList());
    }

    @Override
    public List<GwhDataFormatConfiguration> getResourceByProfile(String profile) {
        return gwhDataFormatService.getByProfile(profile)
             .stream()
             .map(item -> new GwhDataFormatConfiguration(item.getDataFormatName(),
                item.getDataFormatConfig(),
                item.getDataFormatDefinition()))
             .collect(Collectors.toList());
    }

    @Override
    public List<GwhDataFormatConfiguration> getResourceByProfileAndRegionAndVersion(String profile, String region, String version) {
        return gwhDataFormatService.getByProfileAndRegionAndVersion(profile, region, version)
             .stream()
             .map(item -> new GwhDataFormatConfiguration(item.getDataFormatName(),
                item.getDataFormatConfig(),
                item.getDataFormatDefinition()))
             .collect(Collectors.toList());
    }



}
