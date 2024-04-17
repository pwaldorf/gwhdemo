package com.pw.gwhparser.parser.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.pw.gwhparser.parser.enums.ValueType;

public class PathMapping {

    private String path;
    private String element;
    private ValueType type;
    private Map<String, String> conditions;


    public PathMapping() {
    }

    public PathMapping(String element, ValueType type) {
        this.element = element;
        this.type = type;
    }

    public PathMapping(String path, String element, ValueType type) {
        this.path = path;
        this.element = element;
        this.type = type;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getElement() {
        return element;
    }
    public void setElement(String element) {
        this.element = element;
    }
    public ValueType getType() {
        return type;
    }
    public void setType(ValueType type) {
        this.type = type;
    }

    public void addConditions(String name, String value) {
        if (name == null || name.isEmpty()) {
            return;
        }
        if (value == null || value.isEmpty()) {
            return;
        }

        if (conditions == null) {
            conditions = new HashMap<>();
        }
        conditions.put(name, value);
    }

    public Iterator<Entry<String, String>> getConditions() {
        Map<String, String> map = (conditions != null ? conditions : Collections.emptyMap());
        return map.entrySet().iterator();
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = (conditions != null ? new HashMap<>(conditions) : null);
    }

    @Override
    public String toString() {
        return "PathMapping [path=" + path + ", element=" + element + ", type=" + type + ", conditions=" + conditions
                + "]";
    }

}
