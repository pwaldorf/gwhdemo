package com.pw.dataformat1.flatpackdataformat.loader;

import com.pw.dataformat1.dataformat.loader.GwhDataLoader;
import com.pw.dataformat1.flatpackdataformat.configuration.GwhFlatpackConfiguration;

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
