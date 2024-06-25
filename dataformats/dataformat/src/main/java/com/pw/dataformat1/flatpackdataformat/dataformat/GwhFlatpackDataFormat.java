package com.pw.dataformat1.flatpackdataformat.dataformat;

import org.apache.camel.dataformat.flatpack.FlatpackDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.dataformat1.dataformat.dataformat.GwhDataFormat;
import com.pw.dataformat1.dataformat.loader.GwhDataLoader;
import com.pw.dataformat1.flatpackdataformat.configuration.GwhFlatpackConfiguration;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.dataformat.flatpack.enabled", havingValue = "true", matchIfMissing = false)
public class GwhFlatpackDataFormat implements GwhDataFormat {

    private final GwhDataLoader<GwhFlatpackConfiguration> dataLoader;

    private GwhFlatpackConfiguration dataFormatConfig = null;

    private FlatpackDataFormat flatpackDataFormat = null;

    public GwhFlatpackDataFormat(GwhDataLoader<GwhFlatpackConfiguration> dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public DataFormat getDataFormat(String formatName) {

        if (dataFormatConfig == null) {
            dataFormatConfig = loadDataFormatConfiguration(formatName);
        }

        flatpackDataFormat = new FlatpackDataFormat();
        flatpackDataFormat.setDefinition(dataFormatConfig.getFormatDefinition());
        flatpackDataFormat.setFixed(dataFormatConfig.isFixedFile());
        flatpackDataFormat.setDelimiter(dataFormatConfig.getDelimiter());
        flatpackDataFormat.setTextQualifier(dataFormatConfig.getTextQualifier());
        flatpackDataFormat.setIgnoreFirstRecord(dataFormatConfig.isIgnoreFirstRecord());
        flatpackDataFormat.setAllowShortLines(dataFormatConfig.isAllowShortLines());
        flatpackDataFormat.setIgnoreExtraColumns(dataFormatConfig.isIgnoreExtraColumns());

        return flatpackDataFormat;

    }

    private GwhFlatpackConfiguration loadDataFormatConfiguration(String formatName) {

        GwhFlatpackConfiguration dataFormatConfig = new GwhFlatpackConfiguration();
        dataFormatConfig = dataLoader.load(formatName);
        return dataFormatConfig;

    }

}
