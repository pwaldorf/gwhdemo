package com.pw.gwhparser1.loader.dictionaryconfig;

import com.pw.gwhparser1.loader.ConfigLoaderStrategy;
import com.pw.gwhparser1.parser.model.Dictionary;


public class PropertiesDictionaryLoader implements ConfigLoaderStrategy<Dictionary, DictionaryProperties> {

    @Override
    public void load(Dictionary dictionary, DictionaryProperties properties) {

        dictionary.setDictionaryName(properties.getDictionaryName());
        properties.getElements().stream().forEach(element -> dictionary.add(element));

    }

}
