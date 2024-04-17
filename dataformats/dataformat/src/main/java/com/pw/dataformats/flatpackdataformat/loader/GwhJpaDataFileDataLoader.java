package com.pw.dataformats.flatpackdataformat.loader;

import com.pw.dataformats.dataformat.loader.GwhDataLoader;
import com.pw.dataformats.flatpackdataformat.configuration.GwhFlatpackConfiguration;

public class GwhJpaDataFileDataLoader implements GwhDataLoader<GwhFlatpackConfiguration> {

    @Override
    public GwhFlatpackConfiguration load(String formatName) {

        GwhFlatpackConfiguration configuration = new GwhFlatpackConfiguration();
        configuration.setDelimiter(',');
        configuration.setTextQualifier('"');
        configuration.setFixedFile(false);
        configuration.setFormatDefinition("formatDefinition");
        configuration.setIgnoreFirstRecord(false);
        return configuration;
    }

}
