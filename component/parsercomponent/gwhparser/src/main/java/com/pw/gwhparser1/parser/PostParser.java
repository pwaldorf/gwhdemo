package com.pw.gwhparser1.parser;

import java.util.Iterator;

import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.config.Key;
import com.pw.gwhparser1.parser.model.Entity;
import com.pw.gwhparser1.parser.util.ConfigUtil;

public class PostParser {

    private static final String APPKEY = "APPKEY";
    private static final Object KEY_DELIM = "|";

    private PostParser() {
    }

    public static void process(Entity entity, String appKey, GenParserConfig config) {
        if (entity == null || config == null) {
            return;
        }
        processAppKey(entity, appKey);
        Iterator<String> namesIterator = config.getPostParserKeyNames();
        while (namesIterator.hasNext()) {
            Key key = config.getPostParserKey(namesIterator.next());
            processElement(entity, appKey, config, key);
        }
    }
    private static void processAppKey(Entity entity, String appKey) {
        if (entity == null || appKey == null || appKey.isEmpty()) {
            return;
        }
        entity.set(APPKEY, appKey);
    }

    private static void processElement(Entity entity, String appKey, GenParserConfig config, Key key) {
        if (entity == null || key == null) {
            return;
        }
        String value = getValueForKeyElements(entity, appKey, key);
        if (value != null && !value.isEmpty()) {
            entity.set(key.getName(), value);
        }
    }

    private static String getValueForKeyElements(Entity entity, String appKey, Key elementKey) {
        if (entity == null || elementKey == null) {
            return null;
        }
        StringBuilder sb = null;
        Iterator<String> elementsIterator = elementKey.getElements();
        while(elementsIterator.hasNext()) {
            String elementConfig = elementsIterator.next();
            String valueStr = ConfigUtil.getElementValueString(appKey, entity, elementConfig);
            if (valueStr == null || valueStr.isEmpty()) {
                continue;
            }
            if (sb == null) {
                sb = new StringBuilder();
            }
            if (sb.length() > 0) {
                sb.append(KEY_DELIM);
            }
            sb.append(valueStr);
        }
        return (sb == null ? null : sb.toString());
    }

}
