package com.pw.gwhparser.parser.model;

import java.io.Serializable;
import java.util.Iterator;


public interface Entity extends Serializable{

    default String getAppKey() {
        return (String) get("appKey");
    }

    public Iterator<String> attributes();
    public String getName();
    public void setName(String name);
    public Entity getParent();
    public void setParent(Entity parent);

    public <T> T get(String name);
    public <T> T get(String name, int index);
    public <T> void set(String name, T value);
    public <T> void add(String name, T value);

    public <T> T getByPath(String path);
    public int size();

    boolean equalsIgnoreCase(String key, String expectValue);
    boolean equals(String key, String expectValue);
    boolean isEmpty(String key);

}
