package com.pw.gwhparser1.parser.config;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pw.gwhparser1.loader.parserconfig.ParserConfigProperties;
import com.pw.gwhparser1.parser.enums.ParserFormat;
import com.pw.gwhparser1.parser.enums.ValueType;
import com.pw.gwhparser1.parser.model.Dictionary;
import com.pw.gwhparser1.parser.model.ElementConfig;
import com.pw.gwhparser1.parser.model.PathMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenParserConfig {

    private ParserConfigProperties parserConfigProperties = new ParserConfigProperties();

    // Mappings for XML and JSON Parsing
    private Map<String, List<PathMapping>> pathElementMap = null;
    private Map<String, Key> postParserMap = null;

    // Fields for Fixedlength and CSV Parsing
    private List<ElementConfig> eleConfigs = null;

    private Dictionary dictionary = null;

    // Name of Parser Config
    private String sampleName = null;

    public ParserConfigProperties getParserConfigProperties() {
        return parserConfigProperties;
    }

    public void setParserConfigProperties(ParserConfigProperties parserConfigProperties) {
        this.parserConfigProperties = parserConfigProperties;
    }

    public ParserFormat getFormat() {
        return parserConfigProperties.getFormat();
    }

    public void setFormat(ParserFormat format) {
        parserConfigProperties.setFormat(format);
    }

    public String getCharacterSet() {
        return parserConfigProperties.getCharacterSet();
    }

    public void setCharacterSet(String characterSet) {
        parserConfigProperties.setCharacterSet(characterSet);
    }

    public boolean isCoalescing() {
        return parserConfigProperties.isCoalescing();
    }

    public void setCoalescing(boolean coalescing) {
        parserConfigProperties.setCoalescing(coalescing);
    }

    public List<DateTimeFormatter> getDateFormatters() {
        return parserConfigProperties.getDateFormatters();
    }

    public void setDateFormatters(List<DateTimeFormatter> dateFormatters) {
        parserConfigProperties.setDateFormatters(dateFormatters);
    }

    public void addDateFormatter(DateTimeFormatter formatter) {
        parserConfigProperties.addDateFormatter(formatter);
    }

    public ValueType getDefaultValueType() {
        return parserConfigProperties.getDefaultValueType();
    }

    public void setDefaultValueType(ValueType defaultValueType) {
        parserConfigProperties.setDefaultValueType(defaultValueType);
    }

    public boolean isPreserveCDATA() {
        return parserConfigProperties.isPreserveCDATA();
    }

    public void setPreserveCDATA(boolean preserveCDATA) {
        parserConfigProperties.setPreserveCDATA(preserveCDATA);
    }

    public String getRootEntityName() {
        return parserConfigProperties.getRootEntityName();
    }

    public void setRootEntityName(String rootEntityName) {
        parserConfigProperties.setRootEntityName(rootEntityName);
    }

    public String getStringConcatDelimiter() {
        return parserConfigProperties.getStringConcatDelimiter();
    }

    public void setStringConcatDelimiter(String stringConcatDelimiter) {
        parserConfigProperties.setStringConcatDelimiter(stringConcatDelimiter);
    }

    public char getCsvDelimiter() {
        return parserConfigProperties.getCsvDelimiter();
    }

    public void setCsvDelimiter(char csvDelimiter) {
        parserConfigProperties.setCsvDelimiter(csvDelimiter);
    }

    public boolean isSkipHeader() {
        return parserConfigProperties.isSkipHeader();
    }

    public void setSkipHeader(boolean skipHeader) {
        parserConfigProperties.setSkipHeader(skipHeader);
    }

    public boolean isUseMap() {
        return parserConfigProperties.isUseMap();
    }

    public void setUseMap(boolean useMap) {
        parserConfigProperties.setUseMap(useMap);
    }

    public List<DateTimeFormatter> getTimestampFormatters() {
        return parserConfigProperties.getTimestampFormatters();
    }

    public void setTimestampFormatters(List<DateTimeFormatter> timestampFormatters) {
        parserConfigProperties.setTimestampFormatters(timestampFormatters);
    }

    public void addTimestampFormatter(DateTimeFormatter formatter) {
        parserConfigProperties.addTimestampFormatter(formatter);
    }

    // Logic to for XML and Json Configs
    public Iterator<String> getTagPaths() {
        return (pathElementMap != null ? pathElementMap.keySet().iterator() : Collections.emptyIterator());
    }

    public Iterator<Entry<String, List<PathMapping>>> getMappings() {
        return (pathElementMap != null ? pathElementMap.entrySet().iterator() : Collections.emptyIterator());
    }

    public List<PathMapping> getMappings(String tagPath) {
        return (pathElementMap != null && tagPath != null ? pathElementMap.get(tagPath.toLowerCase()) : Collections.emptyList());
    }

    public void setMappings(Iterator<Entry<String, List<PathMapping>>> mappings) {
        pathElementMap = null;
        if (mappings == null) {
            return;
        }
        while (mappings.hasNext()) {
            Entry<String, List<PathMapping>> entry = mappings.next();
            List<PathMapping> paths = entry.getValue();
            if (paths == null) {
                continue;
            }
            Iterator<PathMapping> it = paths.iterator();
            while (it.hasNext()) {
                addMapping(it.next());
            }
        }
    }

    public void addMapping(String tagPath, String element, String type) {
        addMapping(new PathMapping(tagPath, element, ValueType.valueOf(type)));
    }

    public void addMapping(String tagPath, String element, String type, Map<String, String> conditions) {
        PathMapping mapping = new PathMapping(tagPath, element, ValueType.valueOf(type));
        mapping.setConditions(conditions);
        addMapping(mapping);
    }

    public void addMapping(String tagPath, String element, ValueType type) {
        addMapping(new PathMapping(tagPath, element, type));
    }

    public void addMapping(String tagPath, String element, ValueType type, Map<String, String> conditions) {
        PathMapping mapping = new PathMapping(tagPath, element, type);
        mapping.setConditions(conditions);
        addMapping(mapping);
    }

    public void addMapping(PathMapping mapping) {
        String path = mapping.getPath();
        String element = mapping.getElement();
        if (path == null || element == null) {
            log.info("Path and element are required");
        } else {
            if (pathElementMap == null) {
                pathElementMap = new LinkedHashMap<>();
            }
            String key = path.toLowerCase();
            List<PathMapping> mappingList = pathElementMap.get(key);
            if (mappingList == null) {
                mappingList = new ArrayList<>();
                pathElementMap.put(key, mappingList);
            }
            mappingList.add(mapping);
        }
    }

    public Iterator<String> getPostParserKeyNames() {
        if (postParserMap != null) {
            return postParserMap.keySet().iterator();
        } else {
            Map<String, Key> emptyMap = Collections.emptyMap();
            return emptyMap.keySet().iterator();
        }
    }

    public Key getPostParserKey(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return (postParserMap != null ? postParserMap.get(name.toLowerCase()) : null);
    }

    public void setPostParserKey(String name, Key postParserKey) {
        if (name == null || name.isEmpty()) {
            return;
        }
        String mapKey = name.toLowerCase();
        if (postParserKey != null) {
            if (postParserMap == null) {
                postParserMap = new LinkedHashMap<>();
            }
            postParserMap.put(mapKey, postParserKey);
            if (!postParserKey.hasElements()) {
                log.info("PostParserKey elements are required");
            }
        } else {
            postParserMap.remove(mapKey);
        }
    }

    // Logic to for FixedLength and CSV Configs
    public List<ElementConfig> getEleConfigs() {
        return eleConfigs;
    }

    public void setEleConfigs(List<ElementConfig> eleConfigs) {
        this.eleConfigs = eleConfigs;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

}
