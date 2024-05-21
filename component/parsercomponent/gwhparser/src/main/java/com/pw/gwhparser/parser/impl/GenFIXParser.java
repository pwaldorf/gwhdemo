package com.pw.gwhparser.parser.impl;

import java.io.BufferedReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pw.gwhparser.parser.AbstractParser;
import com.pw.gwhparser.parser.Parser;
import com.pw.gwhparser.parser.PostParser;
import com.pw.gwhparser.parser.config.GenParserConfig;
import com.pw.gwhparser.parser.model.ElementConfig;
import com.pw.gwhparser.parser.model.Entity;
import com.pw.gwhparser.parser.model.EntityFactory;


public class GenFIXParser extends AbstractParser implements Parser {

    private static ThreadLocal<SimpleDateFormat> formatHolder = new ThreadLocal<>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd-HH:mm:ss.SSS");
        }
    };

    public GenFIXParser(String appKey, GenParserConfig config, EntityFactory entityFactory) {
        super(appKey, config, entityFactory);
    }

    public Entity parse(String text) throws RuntimeException {
        if (text == null || config == null || entityFactory == null) {
            throw new IllegalArgumentException("text, config and entityFactory must not be null");
        }
        Entity rootEntity = entityFactory.newRootEntity(config.getRootEntityName(), appKey);
        startEntity(rootEntity);

        try {
            List<ElementConfig> eleConfigs = config.getEleConfigs();
            for (ElementConfig ele : eleConfigs) {
                String name = ele.getName();
                String value = StringUtils.substring(text, ele.getStart(), ele.getStart() + ele.getLength());
                rootEntity.set(name, parseValue(ele, value));
            }
            postProcessing(rootEntity);
        } catch (Exception e) {
            throw new RuntimeException("parse content with error", e);
        }
        return rootEntity;
    }

    public Entity parse(BufferedReader reader) throws RuntimeException {
        throw new RuntimeException("not support");
    }

    private Object parseValue(ElementConfig config, String va) throws ParseException {
        String type = config.getType();
        String value;
        if (StringUtils.isNotEmpty(config.getDefaultValue()) && StringUtils.isEmpty(va)) {
            value = config.getDefaultValue();
        } else {
            value = va;
        }

        if (StringUtils.equalsIgnoreCase("long", type)) {
            return NumberFormat.getInstance().parse(value).longValue();
        }

        if (StringUtils.equalsIgnoreCase("double", type)) {
            return NumberFormat.getInstance().parse(value).doubleValue();
        }

        if (StringUtils.equalsIgnoreCase("date", type)) {
            SimpleDateFormat format = formatHolder.get();
            format.applyPattern(config.getFormat());
            return format.parse(value);
        }

        return StringUtils.trim(value);
    }

    protected void postProcessing(Entity rootEntity) {
        PostParser.process(rootEntity, appKey, config);
    }

    @Override
    public String toString() {
        return "GenFIXParser []";
    }

}