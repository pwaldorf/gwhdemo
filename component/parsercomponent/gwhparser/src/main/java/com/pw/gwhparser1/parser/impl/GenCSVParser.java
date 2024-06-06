package com.pw.gwhparser1.parser.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import com.pw.gwhparser1.parser.AbstractParser;
import com.pw.gwhparser1.parser.Parser;
import com.pw.gwhparser1.parser.PostParser;
import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.model.ElementConfig;
import com.pw.gwhparser1.parser.model.Entity;
import com.pw.gwhparser1.parser.model.EntityFactory;

import lombok.extern.slf4j.Slf4j;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@Slf4j
public class GenCSVParser extends AbstractParser implements Parser {

    private static ThreadLocal<SimpleDateFormat> formatHolder = new ThreadLocal<>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd-HH:mm:ss.SSS");
        }
    };

    public GenCSVParser(String appKey, GenParserConfig config, EntityFactory entityFactory) {
        super(appKey, config, entityFactory);
    }

    public Entity parse(String text) throws RuntimeException {
        if (text == null || config == null || entityFactory == null) {
            throw new IllegalArgumentException("text, config and entityFactory must not be null");
        }

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(IOUtils.toInputStream(text)));
            CSVParser parser = new CSVParser(reader, getCSVFormat())) {
            List<CSVRecord> records = parser.getRecords();

            if (config.isUseMap()) {
                return multiRecord(records);
            } else {
                return singleRecord(records);
            }

        } catch (Exception e) {
            throw new RuntimeException("parse content with error", e);
        }
    }

    public Entity parse(BufferedReader reader) throws RuntimeException {
        if (reader == null || config == null || entityFactory == null) {
            throw new IllegalArgumentException("reader, config and entityFactory must not be null");
        }

        try(CSVParser parser = new CSVParser(reader, getCSVFormat())) {
            List<CSVRecord> records = parser.getRecords();

            if (config.isUseMap()) {
                return multiRecord(records);
            } else {
                return singleRecord(records);
            }

        } catch (Exception e) {
            throw new RuntimeException("parse content with error", e);
        }
    }

    @SuppressWarnings("null")
    private Entity singleRecord(List<CSVRecord> records) {
        Entity rootEntity = entityFactory.newRootEntity(config.getRootEntityName(), appKey);
        startEntity(rootEntity);
        try {
            List<ElementConfig> eleConfigs = config.getEleConfigs();
            CSVRecord record = null;
            if (CollectionUtils.isNotEmpty(records)) {
                record = records.get(0);
            }

            if (ObjectUtils.isEmpty(record)) {
                log.info("CSV parser text is empty");
            }

            for (ElementConfig ele : eleConfigs) {
                String name = ele.getName();
                String value = record.get(ele.getIndex());
                rootEntity.set(name, parseValue(ele, value));
            }
            postProcessing(rootEntity);
        } catch (Exception e) {
            throw new RuntimeException("parse content with error", e);
        }
        return rootEntity;
    }

    private Entity multiRecord(List<CSVRecord> records) {
        Entity rootEntity = entityFactory.newRootEntity(config.getRootEntityName(), appKey);
        startEntity(rootEntity);
        try {
            List<Map<String, Object>> csvlist = new LinkedList<>();
            List<ElementConfig> eleConfigs = config.getEleConfigs();

            if (records.size() > 1 && config.isSkipHeader()) {
                records.remove(0);
            }

            for (CSVRecord record : records) {
                if (ObjectUtils.isEmpty(record)) {
                    log.info("CSV parser text is empty");
                }

                Map<String, Object> map = new LinkedHashMap<>();
                for (ElementConfig ele : eleConfigs) {
                    String name = ele.getName();
                    String value = record.get(ele.getIndex());
                    map.put(name, parseValue(ele, value));
                }
                csvlist.add(map);
            }
            rootEntity.set("csvlist", csvlist);
            postProcessing(rootEntity);
        } catch (Exception e) {
            throw new RuntimeException("parse content with error", e);
        }
        return rootEntity;
    }

    private CSVFormat getCSVFormat() {

        CSVFormat format = CSVFormat.DEFAULT.withDelimiter(config.getCsvDelimiter());

        if (config.isSkipHeader()) {
            format = format.withSkipHeaderRecord(true);
		}

        return format;
    }

    private Object parseValue(ElementConfig config, String va) throws ParseException {
        String type = config.getType();
        String value;
        if (StringUtils.isNotEmpty(config.getDefaultValue()) && StringUtils.isEmpty(va)) {
            value = config.getDefaultValue();
        } else {
            value = va;
        }
        Object v = null;
        if (StringUtils.equalsIgnoreCase("long", type)) {
            Number n = NumberFormat.getInstance().parse(value);
            v = n.longValue();
        } else if (StringUtils.equalsIgnoreCase("double", type)) {
            Number n = NumberFormat.getInstance().parse(value);
            v = n.doubleValue();
        } else if (StringUtils.equalsIgnoreCase("date", type)) {
            SimpleDateFormat format = formatHolder.get();
            format.applyPattern(config.getFormat());
            v = format.parse(value);
        } else if (StringUtils.equalsIgnoreCase("string", type)) {
            v = StringUtils.trim(value);
        } else {
            v = StringUtils.trim(value);
        }
        return v;
    }

    protected void postProcessing(Entity rootEntity) {
        PostParser.process(rootEntity, appKey, config);
    }

    @Override
    public String toString() {
        return "GenFIXParser []";
    }

}
