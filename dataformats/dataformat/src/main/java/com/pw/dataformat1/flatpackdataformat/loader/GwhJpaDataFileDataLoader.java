package com.pw.dataformat1.flatpackdataformat.loader;

import com.google.gson.Gson;
import com.pw.api1.GwhResource;
import com.pw.dataformat1.dataformat.dataformat.GwhDataFormatConfiguration;
import com.pw.dataformat1.dataformat.loader.GwhDataLoader;
import com.pw.dataformat1.flatpackdataformat.configuration.GwhFlatpackConfiguration;
import com.pw.gwhcore1.GwhConfigurationProperties;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "gwh.framework.component.dataformat.flatpack.enabled", havingValue = "true", matchIfMissing = false)
public class GwhJpaDataFileDataLoader implements GwhDataLoader<GwhFlatpackConfiguration> {

    private GwhResource<GwhDataFormatConfiguration> gwhResource;

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final CamelContext camelContext;

    public GwhJpaDataFileDataLoader(GwhConfigurationProperties gwhConfigurationProperties,
                CamelContext camelContext) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.camelContext = camelContext;
    }

    @Override
    public GwhFlatpackConfiguration load(String formatName) {

        if (gwhResource == null) {
            return new GwhFlatpackConfiguration();
        }

        GwhDataFormatConfiguration dataFormatConfiguration = new GwhDataFormatConfiguration();
        GwhFlatpackDataResource gwhFlatpackDataResource = new GwhFlatpackDataResource();

        gwhResource.getResource(gwhConfigurationProperties.getProfile(), gwhConfigurationProperties.getRegion(), gwhConfigurationProperties.getVersion())
            .stream()
            .filter(item -> item.getDataformatName().equals(formatName))
            .findFirst()
            .ifPresent(item -> {
                dataFormatConfiguration.setDataformatName(item.getDataformatName());
                dataFormatConfiguration.setDataformatConfiguration(item.getDataformatConfiguration());
                gwhFlatpackDataResource.setDataformatDefinition(item.getDataformatDefinition());
        });

        // Create as camel bean to use in routes as URL Resource bean:beanname?method=beanmethod
        camelContext.getRegistry().bind("gwhFlatpackDataResource", gwhFlatpackDataResource);

        log.debug("JSON: {}", dataFormatConfiguration.getDataformatConfiguration());
        log.debug("XML: {}", gwhFlatpackDataResource.getDataformatDefinition());

        Gson gson = new Gson();
        GwhFlatpackConfiguration configuration = gson.fromJson(dataFormatConfiguration.getDataformatConfiguration(), GwhFlatpackConfiguration.class);
        return configuration;
    }

    @Autowired
    public void setGwhResource(GwhResource<GwhDataFormatConfiguration> gwhResource) {
        this.gwhResource = gwhResource;
    }
}