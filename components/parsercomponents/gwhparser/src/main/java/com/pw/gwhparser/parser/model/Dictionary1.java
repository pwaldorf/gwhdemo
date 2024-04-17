package com.pw.gwhparser.parser.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dictionary1 {

    private static final Dictionary1 instance;

    private String dictionaryName;
    private Map<String, Element> nameElementsMap;

    static {
        instance = new Dictionary1();
    }

    private Dictionary1() {
        super();
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance");
        }
    }

    public static Dictionary1 getInstance() {
        return instance;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public Iterator<String> elementNames() {
        return (nameElementsMap != null ? nameElementsMap.keySet().iterator() : Collections.emptyIterator());
    }

    public Element getElement(String name) {
        return (nameElementsMap != null && name != null ? nameElementsMap.get(name) : null);
    }

    public int getSize() {
        return (nameElementsMap != null ? nameElementsMap.size() : 0);
    }

    public void add(Element element) {
        if (element == null) {
            return;
        }
        String elementName = element.getName();
        if (elementName == null || elementName.isEmpty()) {
            log.warn("Element name is null or empty");
            return;
        }
        if (nameElementsMap == null) {
            nameElementsMap = new LinkedHashMap<>();
        }
        nameElementsMap.put(elementName, element);
    }

    public void reset() {
        nameElementsMap = null;
    }

    @Override
    public String toString() {
        return "Dictionary [dictionaryName=" + dictionaryName + ", nameElementsMap=" + nameElementsMap + "]";
    }



}
