package com.pw.dataformat1.flatpackdataformat.dataformat;

import org.apache.camel.dataformat.flatpack.FlatpackDataFormat;
import org.apache.camel.spi.DataFormat;

import com.pw.dataformat1.dataformat.dataformat.GwhDataFormat;
import com.pw.dataformat1.dataformat.loader.GwhDataLoader;
import com.pw.dataformat1.flatpackdataformat.configuration.GwhFlatpackConfiguration;


public class GwhFlatpackDataFormat implements GwhDataFormat {

    GwhDataLoader<GwhFlatpackConfiguration> dataLoader;

    GwhFlatpackConfiguration dataFormatConfig = null;

    public GwhFlatpackDataFormat(GwhDataLoader<GwhFlatpackConfiguration> dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public DataFormat getDataFormat(String formatName) {

        if (dataFormatConfig == null) {
            dataFormatConfig = loadDataFormatConfiguration(formatName);
        }

        FlatpackDataFormat dataFormat = new FlatpackDataFormat();
        dataFormat.setDefinition(dataFormatConfig.getFormatDefinition());
        dataFormat.setFixed(dataFormatConfig.isFixedFile());
        dataFormat.setDelimiter(dataFormatConfig.getDelimiter());
        dataFormat.setTextQualifier(dataFormatConfig.getTextQualifier());
        dataFormat.setIgnoreFirstRecord(dataFormatConfig.isIgnoreFirstRecord());
        return dataFormat;
    }

    private GwhFlatpackConfiguration loadDataFormatConfiguration(String formatName) {

        GwhFlatpackConfiguration dataFormatConfig = new GwhFlatpackConfiguration();
        dataFormatConfig = dataLoader.load(formatName);
        return dataFormatConfig;

    }

}
