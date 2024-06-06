package com.pw.gwhparser1.loader.parserconfig;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.pw.gwhparser1.loader.ConfigLoaderStrategy;
import com.pw.gwhparser1.parser.Parser;
import com.pw.gwhparser1.parser.ParserFactory;
import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.config.Key;
import com.pw.gwhparser1.parser.enums.DataType;
import com.pw.gwhparser1.parser.enums.ParserFormat;
import com.pw.gwhparser1.parser.enums.ValueType;
import com.pw.gwhparser1.parser.model.Dictionary;
import com.pw.gwhparser1.parser.model.Element;
import com.pw.gwhparser1.parser.model.Entity;
import com.pw.gwhparser1.parser.model.SimpleEntity;
import com.pw.gwhparser1.parser.model.SimpleEntityFactory;
import com.pw.gwhparser1.parser.util.ConfigUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlParserConfigLoader implements ConfigLoaderStrategy<GenParserConfig, String> {

    private static final String ELEMENT = "Element";
    private static final String ELEMENTS = "Elements";
    private static final String ELEMENT_DELIM = "\\|";

    private static final String MAPPING = "Mapping";
    private static final String NAME = "Name";
    private static final String POST_PARSING = "PostParsing";
    private static final String PROPERTY = "Property";
    private static final String PATH = "Path";
    private static final String TYPE = "Type";
    private static final String VALUE = "Value";

    private static final String PP_PKEY = "PKEY";
    private static final String PP_RELKEY = "RelKey";
    private static final String PP_SUBTYPE = "SubType";
    private static final String PP_TYPE = "TYPE";

    private static final String PROP_FORMAT = "Format";
    private static final String PROP_CHARACTER_SET = "CharacterSet";
    private static final String PROP_COALESCING = "Coalescing";
    private static final String PROP_DATE_FORMAT = "DateFormat";
    private static final String PROP_DEFAULT_VALUE_TYPE = "DefaultValueType";
    private static final String PROP_PRESERVE_CDATA = "PreserveCDATA";
    private static final String PROP_ROOT_ENTITY_NAME = "RootEntityName";
    private static final String PROP_TIMESTAMP_FORMAT = "TimestampFormat";

    private static final String PATH_DELIM = ".";

    private Map<String, Consumer<String>> PROPERTY_ACTIONS;

    private GenParserConfig genConfig;

    public XmlParserConfigLoader() {
    }

    @Override
    public void load(GenParserConfig genConfig, String configData) {
        try {
            getMetadata(genConfig);
            this.genConfig = genConfig;
            parseConfig(configData, true);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot generate parser config instance", e);
        }
    }

    protected void parseConfig(String configData, boolean hasMappings) throws RuntimeException {
        ParserFactory parserFactory = ParserFactory.getInstance();
        if (parserFactory == null) {
            throw new NullPointerException("Cannot create parser factory");
        }
        SimpleEntityFactory entityFactory = SimpleEntityFactory.getInstance();
        Parser parser = parserFactory.getParser(null, genConfig, entityFactory);
        Entity config = parser.parse(configData);

        if (config == null) {
            throw new NullPointerException("config is required");
        }

        setProperties(config);
        if (hasMappings) {
            setMappings(config);
        }
        setPostParserKeys(config);
    }

    private void setProperties(Entity config) {

        List<Entity> properties = config.get(PROPERTY);
        if (CollectionUtils.isEmpty(properties)) {
            return;
        }

        properties.stream()
              .map(property -> new AbstractMap.SimpleEntry<>(property.<String>get(NAME), property.<String>get(VALUE)))
              .filter(entry -> entry.getKey() != null && entry.getValue() != null)
              .forEach(entry -> setProperties(entry.getKey(), entry.getValue()));
    }

    private void setProperties(String name, String value) {

        if (PROPERTY_ACTIONS == null || PROPERTY_ACTIONS.isEmpty()) {
            PROPERTY_ACTIONS = new HashMap<>();
            PROPERTY_ACTIONS.put(PROP_FORMAT, v -> genConfig.setFormat(ParserFormat.valueOf(v)));
            PROPERTY_ACTIONS.put(PROP_CHARACTER_SET, v -> genConfig.setCharacterSet(v));
            PROPERTY_ACTIONS.put(PROP_PRESERVE_CDATA, v -> genConfig.setPreserveCDATA(Boolean.parseBoolean(v)));
            PROPERTY_ACTIONS.put(PROP_COALESCING, v -> genConfig.setCoalescing(Boolean.parseBoolean(v)));
            PROPERTY_ACTIONS.put(PROP_ROOT_ENTITY_NAME, v -> genConfig.setRootEntityName(v));
            PROPERTY_ACTIONS.put(PROP_DATE_FORMAT, v -> genConfig.addDateFormatter(ConfigUtil.getDateFormatter(v)));
            PROPERTY_ACTIONS.put(PROP_TIMESTAMP_FORMAT, v -> genConfig.addTimestampFormatter(ConfigUtil.getTimestampFormatter(v)));
            PROPERTY_ACTIONS.put(PROP_DEFAULT_VALUE_TYPE, v -> genConfig.setDefaultValueType(ValueType.valueOf(v.toUpperCase())));
        }

        Optional.ofNullable(PROPERTY_ACTIONS.get(name))
        .ifPresent(action -> action.accept(value));

    }

    private void setMappings(Entity config) {

        // config.getEntities(MAPPING)
        // .stream()
        // .filter(mapping -> mapping.getString(PATH) != null && mapping.getString(ELEMENT) != null)
        // .forEach(mapping -> genConfig.addMapping(
        //     mapping.getString(PATH),
        //     mapping.getString(ELEMENT),
        //     getValueType(mapping.getString(TYPE), mapping.getString(ELEMENT)) != null ?
        //         getValueType(mapping.getString(TYPE), mapping.getString(ELEMENT)) :
        //         genConfig.getDefaultValueType()
        // ));

        Optional.ofNullable(config.<List<Entity>>get(MAPPING))
        .ifPresent(mappingList -> mappingList.stream()
                .filter(mapping -> {
                    Optional<String> path = Optional.ofNullable(mapping.<String>get(PATH));
                    Optional<String> element = Optional.ofNullable(mapping.<String>get(ELEMENT));
                    return path.isPresent() && element.isPresent();
                })
                .forEach(mapping -> {
                    String path = mapping.<String>get(PATH);
                    String element = mapping.<String>get(ELEMENT);
                    ValueType valueType = Optional.ofNullable(getValueType(mapping.<String>get(TYPE), element))
                            .orElse(genConfig.getDefaultValueType());
                    genConfig.addMapping(path, element, valueType);
                }));


    }

    private ValueType getValueType(String cType, String elementName) {

        ValueType type = (cType != null ? ValueType.valueOf(cType.toUpperCase()) : null);
        Dictionary dictionary = genConfig.getDictionary();

        Element element = dictionary.getElement(elementName);
        if (element != null) {
            DataType eType = element.getType();
            if (eType != null && !(DataType.STRING.equals(eType) && ValueType.STRING_CONCAT.equals(type))) {
                type = ValueType.valueOf(eType.toString());
            }
        }
        return (type != null ? type : genConfig.getDefaultValueType());

    }

    private void setPostParserKeys(Entity config) {

        Entity cfgPostParsing = config.<Entity>get(POST_PARSING);
        if (cfgPostParsing == null) {
            return;
        }
        Iterator<String> attributesIterator = cfgPostParsing.attributes();
        while (attributesIterator.hasNext()) {
            String name = attributesIterator.next();
            Entity cfgPKey = cfgPostParsing.<Entity>get(name);
            if (cfgPKey == null) {
                continue;
            }
            String elements = cfgPKey.<String>get(ELEMENTS);
            if (elements != null) {
                Key key = new Key(name);
                setKeyElements(key, elements);
                genConfig.setPostParserKey(name, key);

                if (key != null) {
                    List<String> invalidElements = checkInvalidElements(key.getElements());
                    if (invalidElements != null && !invalidElements.isEmpty()) {
                        log.info("Invalid elements " + invalidElements);
                    }
                }
            }
        }
    }

    private List<String> checkInvalidElements(Iterator<String> elementsIterator) {
        if (elementsIterator == null) {
            return Collections.emptyList();
        }
        List<String> invalidElements = null;
        while (elementsIterator.hasNext()) {
            String element = elementsIterator.next();
            if (isValidElementConfig(element)) {
                continue;
            }
            if (invalidElements == null) {
                invalidElements = new ArrayList<>();
            }
            invalidElements.add(element);
        }
        return (invalidElements != null ? invalidElements : Collections.emptyList());
    }

    private boolean isValidElementConfig(String elementConfig) {
        if (elementConfig == null || elementConfig.isEmpty()) {
            return false;
        }
        String elementName = elementConfig;
        if(ConfigUtil.hasCDATATags(elementConfig)) {
            elementName = ConfigUtil.removeCDDATATags(elementConfig);
        } else if (elementName.indexOf(PATH_DELIM) != -1) {
            return isValidElementPath(elementName);
        }
        return (ConfigUtil.hasLiteralPrefix(elementName) || genConfig.getDictionary().getElement(elementConfig) != null);
    }

    private boolean isValidElementPath(String path) {
        String [] nodes = path.split(SimpleEntity.PATH_DELIM, -1);
        if (nodes == null || nodes.length <= 1) {
            return false;
        }
        int count = nodes.length;
        for (int i = 0; i < count; i++) {
            String node = nodes[i];
            if (!isValidElementPathNode(node)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidElementPathNode(String node) {
        if (node == null) {
            return false;
        }
        String elementName = node;
        int pos = node.indexOf(SimpleEntity.INDEX_DELIM_START);
        if (pos != -1) {
            elementName = node.substring(0, pos);
        }
        Element element = genConfig.getDictionary().getElement(elementName);
        if (element == null) {
            return false;
        }
        if (DataType.ENTITY_LIST.equals(element.getType())) {
            String indexStr = null;
            int pos2 = node.indexOf(SimpleEntity.INDEX_DELIM_END);
            if (pos2 != -1) {
                indexStr = node.substring(pos + 1, pos2).trim();
            } else {
                indexStr = node.substring(pos + 1).trim();
            }
            return isValidEntityListIndex(elementName, indexStr);
        }
        return true;
    }

    private boolean isValidEntityListIndex(String elementName, String indexStr) {
        try {
            int index = Integer.parseInt(indexStr);
            if (index < 0) {
                log.info("Invalid index " + indexStr + " for element " + elementName);
                return false;
            }
        } catch (Exception e) {
            log.info("Invalid index " + indexStr + " for element " + elementName);
            return false;
        }
        return true;
    }

    private void setKeyElements(Key key, String elements) {
        if (key != null && elements != null) {
            String[] elementArray = elements.split(ELEMENT_DELIM);
            int count = ArrayUtils.getLength(elementArray);
            for (int i = 0; i < count; i++) {
                key.addElements(elementArray[i]);
            }
        }
    }

    private void getMetadata(GenParserConfig genConfig) {
        genConfig.setFormat(ParserFormat.XML);
        genConfig.setSampleName("XMLPARSER");
        genConfig.addMapping("/ParserConfig/Property", PROPERTY, ValueType.ENTITY_LIST);
        genConfig.addMapping("/ParserConfig/Property/Name", NAME, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/Property/Value", VALUE, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/Property/@name", NAME, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/Property/@value", VALUE, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/Mapping", MAPPING, ValueType.ENTITY_LIST);
        genConfig.addMapping("/ParserConfig/Mapping/Element", ELEMENT, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/Mapping/Path", PATH, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/Mapping/Type", TYPE, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/Mapping/@element", ELEMENT, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/Mapping/@type", TYPE, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/PostParsing", POST_PARSING, ValueType.ENTITY);
        genConfig.addMapping("/ParserConfig/PostParsing/PKey", PP_PKEY, ValueType.ENTITY);
        genConfig.addMapping("/ParserConfig/PostParsing/PKey/Elements", ELEMENTS, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/PostParsing/PKey/@elements", ELEMENTS, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/PostParsing/RelKey", PP_RELKEY, ValueType.ENTITY);
        genConfig.addMapping("/ParserConfig/PostParsing/RelKey/Elements", ELEMENTS, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/PostParsing/RelKey/@elements", ELEMENTS, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/PostParsing/Type", PP_TYPE, ValueType.ENTITY);
        genConfig.addMapping("/ParserConfig/PostParsing/Type/Elements", ELEMENTS, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/PostParsing/Type/@elements", ELEMENTS, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/PostParsing/SubType", PP_SUBTYPE, ValueType.ENTITY);
        genConfig.addMapping("/ParserConfig/PostParsing/SubType/Elements", ELEMENTS, ValueType.STRING);
        genConfig.addMapping("/ParserConfig/PostParsing/SubType/@elements", ELEMENTS, ValueType.STRING);
    }

}
