package com.pw.gwhparser1.loader.parserconfig;


import com.pw.gwhparser1.loader.ConfigLoaderStrategy;
import com.pw.gwhparser1.parser.config.GenParserConfig;

public class PropertiesParserConfigLoader implements ConfigLoaderStrategy<GenParserConfig, ParserConfigProperties> {

    @Override
    public void load(GenParserConfig config, ParserConfigProperties parserConfigProperties) {
        config.setParserConfigProperties(parserConfigProperties);
    }

}
