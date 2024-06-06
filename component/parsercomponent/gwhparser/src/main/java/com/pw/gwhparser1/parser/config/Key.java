package com.pw.gwhparser1.parser.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Key {

    private String name;
    private List<String> elements;

    public Key(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterator<String> getElements() {
        return (elements != null ? elements.iterator() : Collections.emptyIterator());
    }

    public boolean hasElements() {
        return elements != null && !elements.isEmpty();
    }

    public void addElements(String element) {
        if (elements != null) {
            if (element == null) {
                elements = new ArrayList<>();
            }
            elements.add(element);
        }
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{name: ").append(name);
        sb.append(", elements: ").append(elements).append("}");
        return sb.toString();
    }
}
