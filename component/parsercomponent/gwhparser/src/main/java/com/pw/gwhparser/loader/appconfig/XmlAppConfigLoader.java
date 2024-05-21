package com.pw.gwhparser.loader.appconfig;


import com.pw.gwhparser.loader.ConfigLoaderStrategy;
import com.pw.gwhparser.loader.appconfig.sample.SampleReader;
import com.pw.gwhparser.loader.appconfig.sample.SampleReaderFactory;
import com.pw.gwhparser.parser.config.GenParserConfig;
import com.pw.gwhparser.parser.enums.ParserFormat;


public class XmlAppConfigLoader implements ConfigLoaderStrategy<GenParserConfig, String> {

    public XmlAppConfigLoader() {
    }

    @Override
    public void load(GenParserConfig config, String data) {

        SampleReaderFactory readerFactory = SampleReaderFactory.getInstance();
        SampleReader reader = readerFactory.getReader(config);
        if (config.getFormat().name().equals(ParserFormat.FIXEDLENGTH.name()) || config.getFormat().name().equals(ParserFormat.CSV.name())) {
            config.setEleConfigs(reader.readSample(data));
        } else {
            config.addMapping(reader.readSample(data));
        }

    }

}
