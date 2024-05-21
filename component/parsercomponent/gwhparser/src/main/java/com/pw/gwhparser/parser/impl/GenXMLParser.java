package com.pw.gwhparser.parser.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.apache.commons.lang3.StringUtils;

import com.pw.gwhparser.parser.AbstractParser;
import com.pw.gwhparser.parser.Parser;
import com.pw.gwhparser.parser.PostParser;
import com.pw.gwhparser.parser.config.GenParserConfig;
import com.pw.gwhparser.parser.constants.ParserConstants;
import com.pw.gwhparser.parser.model.Entity;
import com.pw.gwhparser.parser.model.EntityFactory;
import com.pw.gwhparser.parser.model.PathMapping;
import com.pw.gwhparser.parser.util.ResourceUtil;
import com.pw.gwhparser.parser.util.ValueUtil;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenXMLParser extends AbstractParser implements Parser {

    public static final String REPORT_CDATA_EVENT = "http://java.sun.com/xml/stream/properties/report-cdata-event";

    private static ThreadLocal<XMLInputFactory> xmlInputFactory = new ThreadLocal<>();

    protected StringBuilder elementValue = new StringBuilder();
    protected Map<String, String> attributes = new HashMap<>();

    public GenXMLParser(String appKey, GenParserConfig config, EntityFactory entityFactory) {
        super(appKey, config, entityFactory);
    }

    @Override
    public Entity parse(String text) throws RuntimeException {
        if (text == null || config == null || entityFactory == null) {
            throw new NullPointerException("Text, config and entityFactory are required");
        }
        Entity rootEntity = entityFactory.newRootEntity(config.getRootEntityName(), appKey);
        startEntity(rootEntity);
        XMLEventReader reader = null;
        try (ByteArrayInputStream inStream = new ByteArrayInputStream(text.getBytes())) {
            reader = getXMLReader(inStream, config);
            while(reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event == null) {
                    continue;
                }
                if (event.isStartElement()) {
                    parseStartElement(event.asStartElement());
                } else if (event.isEndElement()) {
                    parseEndElement(event.asEndElement());
                } else if (event.isCharacters()) {
                    parseCharacters(event.asCharacters());
                }
            }
            postProcessing(rootEntity);
        } catch (Exception e) {
            log.info("XML Parser Error: " + e);
            throw new RuntimeException("XML Parser Error", e);
        } finally {
            ResourceUtil.release(reader);
        }
        return rootEntity;
    }

    public Entity parse(BufferedReader reader) throws RuntimeException {
        throw new RuntimeException("not support");
    }

    protected void parseStartElement(StartElement element) {
        if (element == null || config == null) {
            throw new NullPointerException("Element and config are required");
        }
        String localName = element.getName().getLocalPart();
        String tagPathString = addTagPathLast(localName);
        setMappingsForElement(tagPathString);
        checkStartEntityForMappings();
        startElementValue();
        attributes.clear();
        Iterator<?> attributesIterator = element.getAttributes();
        while (attributesIterator.hasNext()) {
            parseStartElementAttribute(tagPathString, (Attribute) attributesIterator.next());
        }
    }

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
        String attrbuteTagPath = new StringBuilder(tagPathString).append(ParserConstants.PATH_DELIM).append(ValueUtil.AMPERSAND)
                                                                 .append(attributeName).toString();
        List<PathMapping> attributeMappings = config.getMappings(attrbuteTagPath);
        setValue(attributeMappings, attributeValue);
    }

    protected void parseEndElement(EndElement element) {
        if (element == null) {
            throw new NullPointerException("Element is required");
        }
        setValue(getElementValueString());
        checkEndEntity();
        removeTagPathLast();
        this.pathMappings = null;
    }

    @Override
    protected boolean isValid(PathMapping pathMap) {
        if (pathMap == null) {
            return false;
        }
        Iterator<Entry<String, String>> condIterator = pathMap.getConditions();
        while (condIterator.hasNext()) {
            Entry<String, String> cond = condIterator.next();
            String condName = cond.getKey();
            String condValue = cond.getValue();
            String attrValue = attributes.get(condName);
            if (!(condValue != null ? condValue.equals(attrValue) : (attrValue == null))) {
                return false;
            }
        }
        return true;
    }

    protected void parseCharacters(Characters characters) {
        if (characters == null) {
            throw new NullPointerException("Characters is required");
        }
        Characters ce = characters.asCharacters();
        String value = ce.getData();
        if (value == null) {
            return;
        }
        boolean isCDATA = ce.isCData();
        if (isCDATA) {
            boolean isPreserveCDATA = (config != null ? config.isPreserveCDATA() : false);
            if (isPreserveCDATA) {
                appendElementValue(ValueUtil.CDATA_START);
                appendElementValue(value);
                appendElementValue(ValueUtil.CDATA_END);
            }
        } else {
            appendElementValue(trimSpace(value));
        }
    }

    protected void postProcessing(Entity rootEntity) {
        PostParser.process(rootEntity, appKey, config);
    }

    protected void startElementValue() {
        if (elementValue != null) {
            elementValue.setLength(0);
        }
    }

    protected void appendElementValue(String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (elementValue == null) {
            elementValue = new StringBuilder();
        }
        elementValue.append(value);
    }

    protected String getElementValueString() {
        return (elementValue != null ? elementValue.toString() : ParserConstants.BLANK);
    }

    protected static XMLEventReader getXMLReader(InputStream inStream, GenParserConfig config) throws XMLStreamException {
        if (inStream == null || config == null) {
            throw new NullPointerException("InputStream and config are required");
        }
        XMLInputFactory factory = getXmlInputFactory();
        setXMLInputFactoryProperties(factory, config);
        String encoding = config.getCharacterSet();
        if (encoding != null) {
            return factory.createXMLEventReader(inStream, encoding);
        }
        return factory.createXMLEventReader(inStream);
    }

    protected static XMLInputFactory getXmlInputFactory() {
        if (xmlInputFactory.get() == null) {
            xmlInputFactory.set(XMLInputFactory.newInstance());
        }
        return xmlInputFactory.get();
    }

    protected static void setXMLInputFactoryProperties(XMLInputFactory factory, GenParserConfig config) {
        if (factory == null || config == null) {
            throw new NullPointerException("Factory and config are required");
        }
        if (config.isPreserveCDATA()) {
            setXMLInputFactoryProperties(factory, REPORT_CDATA_EVENT, true);
        }
    }

    protected static void setXMLInputFactoryProperties(XMLInputFactory factory, String key, Object value) {
        if (factory == null || key == null) {
            throw new NullPointerException("Factory and key are required");
        }
        if (factory.isPropertySupported(key)) {
            factory.setProperty(key, value);
        } else {
            log.info("Property " + key + " is not supported");
        }
    }

    protected static String trimSpace(String value) {
        try {
            if (StringUtils.isBlank(value)) {
                return value;
            }
            char[] chars = value.toCharArray();
            int len = chars.length;
            int st = 0;

            while ((st < len) && (chars[st] == ' ' || chars[st]  == '\t')) {
                st++;
            }
            while ((st < len) && (chars[len - 1] == ' ' || chars[len - 1]  == '\t')) {
                st++;
            }
            return ((st > 0) || (len < value.length())) ? StringUtils.substring(value, st, len) : value;
        } finally {
            log.info(value);
        }
    }

}
