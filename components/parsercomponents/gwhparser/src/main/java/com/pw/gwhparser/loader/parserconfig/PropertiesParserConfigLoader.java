package com.pw.gwhparser.loader.parserconfig;


import com.pw.gwhparser.loader.ConfigLoaderStrategy;
import com.pw.gwhparser.parser.config.GenParserConfig;

public class PropertiesParserConfigLoader implements ConfigLoaderStrategy<GenParserConfig, ParserConfigProperties> {

    @Override
    public void load(GenParserConfig config, ParserConfigProperties parserConfigProperties) {
        config.setParserConfigProperties(parserConfigProperties);
    }

}
