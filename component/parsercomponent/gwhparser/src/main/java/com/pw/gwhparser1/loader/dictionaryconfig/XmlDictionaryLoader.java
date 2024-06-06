package com.pw.gwhparser1.loader.dictionaryconfig;

import java.util.List;

import com.pw.gwhparser1.loader.ConfigLoaderStrategy;
import com.pw.gwhparser1.parser.Parser;
import com.pw.gwhparser1.parser.ParserFactory;
import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.enums.DataType;
import com.pw.gwhparser1.parser.enums.ParserFormat;
import com.pw.gwhparser1.parser.enums.ValueType;
import com.pw.gwhparser1.parser.model.Dictionary;
import com.pw.gwhparser1.parser.model.Element;
import com.pw.gwhparser1.parser.model.Entity;
import com.pw.gwhparser1.parser.model.SimpleEntityFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlDictionaryLoader implements ConfigLoaderStrategy<Dictionary, String> {

    private static final String NAME = "Name";
    private static final String PROPERTY = "Property";
    private static final String TYPE = "Type";
    private static final String VALUE = "Value";
    private static final String ELEMENT = "Element";
    private static final DataType DEFAULT_DATA_TYPE = DataType.STRING;
    private static final String DESCRIPTION = "Description";
    private static final String DICTIONARY_NAME = "DictionaryName";
    private static final String EDGMID = "EDGMID";
    private static final String PROP_DEFAULT_DATA_TYPE = "DefaultDataType";

    public XmlDictionaryLoader() {
    }

    public void load(Dictionary dictionary, String configData) {

        try {
            ParserFactory parserFactory = ParserFactory.getInstance();
            SimpleEntityFactory entityFactory = SimpleEntityFactory.getInstance();
            Parser parser = parserFactory.getParser(null, getMetadata(), entityFactory);
            Entity cfgDictionary = parser.parse(configData);
            setElements(dictionary, cfgDictionary, getDefaultDataType(cfgDictionary));
        } catch (Exception e) {
            log.error("Cannot config dictionary", e);
            throw new IllegalStateException("Cannot config dictionary", e);
        }

    }

    private DataType getDefaultDataType(Entity cfgDictionary) {
        if (cfgDictionary == null) {
            throw new IllegalArgumentException("Dictionary is null");
        }

        DataType defaultDataType = DEFAULT_DATA_TYPE;
        List<Entity> properties = cfgDictionary.get(PROPERTY);
        if (properties != null && !properties.isEmpty()) {
            for (Entity property : properties) {
                if (property == null) {
                    continue;
                }
                String name = property.get(NAME);
                String value = property.get(VALUE);
                if (name != null && value != null && name.trim().equalsIgnoreCase(PROP_DEFAULT_DATA_TYPE)) {
                    defaultDataType = DataType.valueOf(value.trim().toUpperCase());
                }
            }
        }
        return defaultDataType;
    }

    @SuppressWarnings("null")
    private void setElements(Dictionary dictionary, Entity cfgDictionary, DataType defaultDataType) {
        if (cfgDictionary == null || dictionary == null) {
            throw new IllegalArgumentException("Dictionary is null");
        }

        dictionary.reset();
        dictionary.setDictionaryName(cfgDictionary.<String>get(DICTIONARY_NAME));

        List<Entity> elements = cfgDictionary.get(ELEMENT);
        if (elements != null && !elements.isEmpty()) {
            log.info("Dictionary elements: {}", elements.size());
        }
        for (Entity element : elements) {
            String name = element.get(NAME);
            String description = element.get(DESCRIPTION);
            String type = element.get(TYPE);
            String edgmid = element.get(EDGMID);
            if (name == null || name.isEmpty()) {
                continue;
            }
            DataType dataType = (type != null ? DataType.valueOf(type.toUpperCase()) : null);
            Element element2 = new Element(name, description, (dataType != null ? dataType : defaultDataType), edgmid);
            dictionary.add(element2);
        }
    }

    private GenParserConfig getMetadata() {
        GenParserConfig config = new GenParserConfig();
        config.setFormat(ParserFormat.XML);
        config.addMapping("/Dictionary/DictionaryName", DICTIONARY_NAME, ValueType.STRING);
        config.addMapping("/Dictionary/Property", PROPERTY, ValueType.ENTITY_LIST);
        config.addMapping("/Dictionary/Property/Name", NAME, ValueType.STRING);
        config.addMapping("/Dictionary/Property/Value", VALUE, ValueType.STRING);
        config.addMapping("/Dictionary/Property/@name", NAME, ValueType.STRING);
        config.addMapping("/Dictionary/Property/@value", VALUE, ValueType.STRING);
        config.addMapping("/Dictionary/Element", ELEMENT, ValueType.ENTITY_LIST);
        config.addMapping("/Dictionary/Element/Name", NAME, ValueType.STRING);
        config.addMapping("/Dictionary/Element/Description", DESCRIPTION, ValueType.STRING);
        config.addMapping("/Dictionary/Element/Type", TYPE, ValueType.STRING);
        config.addMapping("/Dictionary/Element/EDGMID", EDGMID, ValueType.STRING);
        config.addMapping("/Dictionary/Element/@name", NAME, ValueType.STRING);
        config.addMapping("/Dictionary/Element/@description", DESCRIPTION, ValueType.STRING);
        config.addMapping("/Dictionary/Element/@type", TYPE, ValueType.STRING);
        config.addMapping("/Dictionary/Element/@edgmid", EDGMID, ValueType.STRING);
        return config;
    }

}
