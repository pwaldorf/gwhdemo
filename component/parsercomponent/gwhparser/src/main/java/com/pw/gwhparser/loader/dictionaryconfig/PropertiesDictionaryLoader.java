package com.pw.gwhparser.loader.dictionaryconfig;

import com.pw.gwhparser.loader.ConfigLoaderStrategy;
import com.pw.gwhparser.parser.model.Dictionary;


public class PropertiesDictionaryLoader implements ConfigLoaderStrategy<Dictionary, DictionaryProperties> {

    @Override
    public void load(Dictionary dictionary, DictionaryProperties properties) {

        dictionary.setDictionaryName(properties.getDictionaryName());
        properties.getElements().stream().forEach(element -> dictionary.add(element));

    }

}
