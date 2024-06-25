package com.pw.dataformat1.flatpackdataformat.configuration;

import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pw.dataformat1.flatpackdataformat.dataformat.GwhFlatpackDataFormat;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.dataformat.flatpack.enabled", havingValue = "true", matchIfMissing = false)
public class GwhFlatpackDataFormatConfiguration {

    @Value("${gwh.dataformat.format.name}")
    private String formatName;

    private final GwhFlatpackDataFormat gwhFlatpackDataFormat;

    public GwhFlatpackDataFormatConfiguration(GwhFlatpackDataFormat gwhFlatpackDataFormat) {
        this.gwhFlatpackDataFormat = gwhFlatpackDataFormat;
    }

    @Bean("ftpDataFormat")
    public DataFormat gwhFlatpackDataFormat() {
        return gwhFlatpackDataFormat.getDataFormat(formatName);
    }

}
