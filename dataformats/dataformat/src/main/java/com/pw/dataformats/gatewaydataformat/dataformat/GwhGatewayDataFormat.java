package com.pw.dataformats.gatewaydataformat.dataformat;


import org.apache.camel.spi.DataFormat;

import com.pw.dataformats.dataformat.dataformat.GwhDataFormat;
import com.pw.dataformats.dataformat.loader.GwhDataLoader;
import com.pw.dataformats.gatewaydataformat.configuration.GwhGatewayConfiguration;


public class GwhGatewayDataFormat implements GwhDataFormat {

    GwhDataLoader<GwhGatewayConfiguration> dataLoader;

    GwhGatewayConfiguration dataFormatConfig = null;

    public GwhGatewayDataFormat(GwhDataLoader<GwhGatewayConfiguration> dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public DataFormat getDataFormat(String formatName) {
        if (dataFormatConfig == null) {
            dataFormatConfig = loadDataFormatConfiguration(formatName);
        }

        GatewayDataFormat dataFormat = new GatewayDataFormat();
        dataFormat.setCharacterSet(dataFormatConfig.getCharacterSet());
        dataFormat.setDefaultValueType(dataFormatConfig.getDefaultValueType());
        dataFormat.setFormat(dataFormatConfig.getFormat());
        dataFormat.setPreserveCDATA(dataFormatConfig.isPreserveCDATA());
        dataFormat.setRootEntityName(dataFormatConfig.getRootEntityName());
        dataFormat.setStringConcatDelimiter(dataFormatConfig.getStringConcatDelimiter());
        dataFormat.setDateFormatters(dataFormatConfig.getDateFormatters());
        dataFormat.setTimestampFormatters(dataFormatConfig.getTimestampFormatters());
        dataFormat.setCsvDelimiter(dataFormatConfig.getCsvDelimiter());
        dataFormat.setSkipHeader(dataFormatConfig.isSkipHeader());
        dataFormat.setUseMap(dataFormatConfig.isUseMap());
        dataFormat.setAppConfig(dataFormatConfig.getAppConfig());

        return dataFormat;
    }

    private GwhGatewayConfiguration loadDataFormatConfiguration(String formatName) {

        GwhGatewayConfiguration dataFormatConfig = new GwhGatewayConfiguration();
        dataFormatConfig = dataLoader.load(formatName);
        return dataFormatConfig;

    }

}