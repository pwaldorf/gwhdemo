package com.pw.gwhparser.loader.appconfig.sample.impl;


import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;

import com.pw.gwhparser.loader.appconfig.sample.SampleReader;
import com.pw.gwhparser.loader.appconfig.sample.SampleUtil;
import com.pw.gwhparser.parser.config.GenParserConfig;
import com.pw.gwhparser.parser.constants.ParserConstants;
import com.pw.gwhparser.parser.enums.ValueType;
import com.pw.gwhparser.parser.impl.GenXMLParser;
import com.pw.gwhparser.parser.model.Entity;
import com.pw.gwhparser.parser.model.EntityFactory;
import com.pw.gwhparser.parser.model.PathMapping;
import com.pw.gwhparser.parser.model.SimpleEntityFactory;
import com.pw.gwhparser.parser.util.ConfigUtil;
import com.pw.gwhparser.parser.util.ValueUtil;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


public class GenXMLSampleReader implements SampleReader {

    private static final GenXMLSampleReader INSTANCE;

    static {
        try {
            INSTANCE = new GenXMLSampleReader();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GenXMLSampleReader() {
        super();
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static GenXMLSampleReader getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Entry<String, List<PathMapping>>> readSample(String sample) throws RuntimeException {
        GenParserConfig config = new GenParserConfig();
        config.setRootEntityName(ParserConstants.ROOT);
        config.setCharacterSet(ParserConstants.SAMPLE_CHARSET_NAME);
        Parser sampleParser = new Parser(null, config, SimpleEntityFactory.getInstance());
        sampleParser.parse(sample);
        return sampleParser.getMappings();
    }

    private class Parser extends GenXMLParser {

        GenParserConfig sampleConfig;
        boolean isStringConcat = false;
        Map<String, String> conditions = new HashMap<>();

        public Parser(String appKey, GenParserConfig config, EntityFactory entityFactory) {
            super(appKey, config, entityFactory);
            sampleConfig = new GenParserConfig();
        }

        public Iterator<Entry<String, List<PathMapping>>> getMappings() {
            return sampleConfig.getMappings();
        }

        @Override
        protected void setValue(Object valueObj) {
            String elementTagPath = getTagPathString();
            SampleUtil.addPathMapping(sampleConfig, elementTagPath, (String) valueObj, conditions);
            startElementValue();
            if (isStringConcat) {
                overridePathMappingType(elementTagPath, ValueType.STRING_CONCAT);
                isStringConcat = false;
            }
            conditions.clear();
        }

        @Override
        protected void parseStartElementAttribute(String tagPathString, Attribute attribute) {
            if (tagPathString == null || attribute == null) {
                return;
            }
            QName qName = attribute.getName();
            String attributeName = qName.getLocalPart();
            String attributeValue = attribute.getValue().trim();
            if (attributeName == null || attributeValue == null) {
                return;
            }
            attributes.put(attributeName, attributeValue);
            String attributePrefix = qName.getPrefix();
            if (!isProcessedSpecialAttribute(tagPathString, attributePrefix, attributeName, attributeValue)) {
                String atttributeTagPath = new StringBuilder(tagPathString).append(ParserConstants.PATH_DELIM)
                    .append(ValueUtil.AMPERSAND).append(attributeName).toString();
                SampleUtil.addPathMapping(sampleConfig, atttributeTagPath, attributeValue);
            }
        }

        protected boolean isProcessedSpecialAttribute(String tagPathString, String attributePrefix,
                        String attributeName, String attributeValue) {

                        return (isNamespaceAttribute(attributeName, attributeValue)
                            || isSetDirectiveAttribute(tagPathString, attributePrefix, attributeName, attributeValue)
                            || isSetConditionAttribute(attributeName, attributeValue));
        }

        protected boolean isNamespaceAttribute(String attributeName, String attributeValue) {
            return ((attributeName != null && attributeName.startsWith(ParserConstants.XMLNS))
                || (attributeValue != null && attributeValue.startsWith(ParserConstants.HTTP)));
        }

        protected boolean isSetDirectiveAttribute(String tagPathString, String attributePrefix, String attributeName,
            String attributeValue) {
            return (isValidDirectiveNamespace(attributePrefix)
                && (isSetEntityDirective(tagPathString, attributeName, attributeValue)
                   || isSetStringConcatDirective(attributeName, attributeValue)));
        }

        protected boolean isValidDirectiveNamespace(String attributePrefix) {
            if (attributePrefix == null) {
                return true;
            }
            String trimmed = attributePrefix.trim();
            return (trimmed.isEmpty() || ParserConstants.GBM_NAMESPACE_PREFIX.equalsIgnoreCase(trimmed));
        }

        protected boolean isSetEntityDirective(String tagPathString, String attributeName, String attributeValue) {
            if (ParserConstants.DIRECTIVE_ENTRY_LIST.equalsIgnoreCase(attributeName)
                || ParserConstants.DIRECTIVE_ENTRY.equalsIgnoreCase(attributeName)) {
                    SampleUtil.addPathMapping(sampleConfig, tagPathString, attributeValue);
                    return true;
                }
                return false;
        }

        protected boolean isSetStringConcatDirective(String attributeName, String attributeValue) {
            if (ParserConstants.DIRECTIVE_STRING_CONCAT.equalsIgnoreCase(attributeName)) {
                    isStringConcat = Boolean.parseBoolean(attributeValue);
                    return true;
            }
            return false;
        }

        protected boolean isSetConditionAttribute(String attributeName, String attributeValue) {
            if (ConfigUtil.hasConditionalPrefix(attributeValue)) {
                String conditionValue = ConfigUtil.getSuffix(attributeValue, ParserConstants.PREFIX_SEPARATOR);
                if (conditionValue != null && !conditionValue.isEmpty()) {
                    conditions.put(attributeName, conditionValue);
                }
                return true;
            }
            return false;
        }

        @SuppressWarnings("null")
        protected void overridePathMappingType(String elementTagPath, ValueType type) {
            List<PathMapping> elementPathMappings = sampleConfig.getMappings(elementTagPath);
            if (elementPathMappings != null || type == null) {
                return;
            }
            Iterator<PathMapping> iterator = elementPathMappings.iterator();
            while (iterator.hasNext()) {
                PathMapping pathMapping = iterator.next();
                if (pathMapping == null) {
                    return;
                }
                pathMapping.setType(type);
            }
        }

        @Override
        protected void postProcessing(Entity rooEntity) {
            return;
        }

    }

}
