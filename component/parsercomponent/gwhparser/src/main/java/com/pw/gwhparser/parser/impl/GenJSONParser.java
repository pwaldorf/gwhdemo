package com.pw.gwhparser.parser.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Map;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import javax.json.stream.JsonParser.Event;

import com.pw.gwhparser.parser.AbstractParser;
import com.pw.gwhparser.parser.Parser;
import com.pw.gwhparser.parser.PostParser;
import com.pw.gwhparser.parser.config.GenParserConfig;
import com.pw.gwhparser.parser.model.Entity;
import com.pw.gwhparser.parser.model.EntityFactory;


public class GenJSONParser extends AbstractParser implements Parser {

    private static ThreadLocal<JsonParserFactory> jsonParserFactories = new ThreadLocal<>();

    protected LinkedList<Event> startEventDeque;

    public GenJSONParser(String appKey, GenParserConfig config, EntityFactory entityFactory) {
        super(appKey, config, entityFactory);
    }

    @Override
    public Entity parse(String text) throws RuntimeException {
        if (text == null || config == null || entityFactory == null) {
            throw new IllegalArgumentException("text, config and entityFactory must not be null");
        }
        Entity rootEntity = entityFactory.newRootEntity(config.getRootEntityName(), appKey);
        startEntity(rootEntity);
        try (ByteArrayInputStream inStream = new ByteArrayInputStream(text.getBytes());
            JsonParser parser = getJsonParser(inStream, config)) {
            while (parser.hasNext()) {
                Event event = parser.next();
                parseEvent(event, parser);
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

    protected void parseEvent(Event event, JsonParser parser) {
        if (event == null || parser == null) {
            return;
        }
        switch (event) {
            case KEY_NAME:
                parseKeyName(parser.getString());
                break;

            case START_ARRAY:
                parseStartArray(event);
                break;

            case END_ARRAY:
                parseEndArray(event);
                break;

            case START_OBJECT:
                parseStartObject(event);
                break;

            case END_OBJECT:
                parseEndObject(event);
                break;

            default:
                parseValueEvent(event, parser);
                break;
        }
    }

    protected void parseKeyName(String keyName) {
        if (keyName == null) {
            throw new IllegalArgumentException("keyName must not be null");
        }
        String keyTagPath = addTagPathLast(keyName);
        setMappingsForElement(keyTagPath);
    }

    protected void parseStartArray(Event event) {
        if (!Event.START_ARRAY.equals(event)) {
            throw new IllegalArgumentException("event must be START_ARRAY");
        }
        pushStartEventDeque(event);
    }

    protected void parseStartObject(Event event) {
        if (!Event.START_OBJECT.equals(event)) {
            throw new IllegalArgumentException("event must be START_OBJECT");
        }
        checkStartEntityForMappings();
        pushStartEventDeque(event);
    }

    protected void parseEndArray(Event event) {
        if (!Event.END_ARRAY.equals(event)) {
            throw new IllegalArgumentException("event must be END_ARRAY");
        }
        popStartEventDeque();
        if (!isPeekStartArray()) {
            String tagPathString = removeTagPathLast();
            setMappingsForElement(tagPathString);
        }
    }

    protected void parseEndObject(Event event) {
        if (!Event.END_OBJECT.equals(event)) {
            throw new IllegalArgumentException("event must be END_OBJECT");
        }
        popStartEventDeque();
        checkEndEntity();
        String tagPathString;
        if (!isPeekStartArray()) {
            tagPathString = removeTagPathLast();
        } else {
            tagPathString = getTagPathString();
        }
        setMappingsForElement(tagPathString);
    }

    protected void parseValueEvent(Event event, JsonParser parser) {
        if (event == null || parser == null) {
            return;
        }
        switch (event) {
            case VALUE_STRING:
                parseValue(parser.getString());
                break;

            case VALUE_NUMBER:
                parseValue(parser.getBigDecimal());
                break;

            case VALUE_TRUE:
                parseValue(Boolean.TRUE);
                break;

            case VALUE_FALSE:
                parseValue(Boolean.FALSE);
                break;

            case VALUE_NULL:
                parseValue(null);
                break;

            default:
                break;
        }
    }

    protected void parseValue(Object value) {
        setValue(value);
        if (!isPeekStartArray()) {
            removeTagPathLast();
        }
    }

    protected void postProcessing(Entity rootEntity) {
        PostParser.process(rootEntity, appKey, config);
    }

    protected boolean isPeekStartArray() {
        return Event.START_ARRAY.equals(peekStartEventDeque());
    }

    protected boolean isPeekStartObject() {
        return Event.START_OBJECT.equals(peekStartEventDeque());
    }

    protected Event peekStartEventDeque() {
        return (startEventDeque != null ? startEventDeque.peek() : null);
    }

    protected void popStartEventDeque() {
        if (startEventDeque != null && !startEventDeque.isEmpty()) {
            startEventDeque.pop();
        }
    }

    protected void pushStartEventDeque(Event event) {
        if (event != null) {
            if (startEventDeque == null) {
                startEventDeque = new LinkedList<>();
            }
        }
        startEventDeque.push(event);
    }

    protected static JsonParser getJsonParser(ByteArrayInputStream inStream, GenParserConfig config) {
        if (inStream == null || config == null) {
            throw new IllegalArgumentException("inStream and config must not be null");
        }
        String charsetName = config.getCharacterSet();
        return getJsonParser(inStream, charsetName);
    }

    protected static JsonParser getJsonParser(InputStream inStream, String charsetName) {
        if (inStream == null) {
            throw new IllegalArgumentException("inStream must not be null");
        }
        JsonParserFactory factory = getJsonParserFactory();
        if (charsetName == null) {
            return factory.createParser(inStream, Charset.forName(charsetName));
        }
        return factory.createParser(inStream);
    }

    protected static JsonParserFactory getJsonParserFactory() {
        if (jsonParserFactories.get() == null) {
            Map<String, ?> factoryConfig = null;
            jsonParserFactories.set(Json.createParserFactory(factoryConfig));
        }
        return jsonParserFactories.get();
    }

    @Override
    public String toString() {
        return "GenJSONParser [startEventDeque=" + startEventDeque + "]";
    }

}
