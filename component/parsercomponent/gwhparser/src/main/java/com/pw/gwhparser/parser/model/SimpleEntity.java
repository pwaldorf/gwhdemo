package com.pw.gwhparser.parser.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SimpleEntity implements Entity {

    public static final long serialVersionUID = 1L;

    public static final String PATH_DELIM = "[.]";
    public static final String INDEX_DELIM_START = "[";
    public static final String INDEX_DELIM_END = "]";

    private HashMap<String, Object> attributeMap;
    private String entityName;
    private transient Entity parent;

    public SimpleEntity() {
        this.entityName = this.getClass().getSimpleName();
        this.parent = null;
        this.attributeMap = new LinkedHashMap<>();
    }

    public SimpleEntity(String name, Entity parent) {
        this.entityName = name;
        this.parent = parent;
        this.attributeMap = new LinkedHashMap<>();
    }

    @Override
    public String getAppKey() {
        return StringUtils.defaultString((String) get("APPKEY"));
    }

    @Override
    public String getName() {
        return entityName;
    }

    @Override
    public void setName(String name) {
        this.entityName = name;
    }

    @Override
    public Entity getParent() {
        return parent;
    }

    @Override
    public void setParent(Entity parent) {
        this.parent = parent;
    }

    public HashMap<String, Object> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(HashMap<String, Object> attributeMap) {
        if (attributeMap == null || attributeMap.isEmpty()) {
            this.attributeMap.clear();
        } else {
            this.attributeMap.putAll(attributeMap);
        }
    }

    public void clear() {
        this.attributeMap.clear();
    }

    @Override
    public Iterator<String> attributes() {
        return attributeMap.keySet().iterator();
    }

    @Override
    public <T> void set(String name, T value) {

        if (name == null || name.isEmpty()) {
            return;
        }

        if (value != null) {
            attributeMap.put(name, value);
        } else if (attributeMap.get(name) != null) {
            attributeMap.remove(name);
        } else {
            System.out.println("Null value for attribute " + name);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String name) {
        if (name == null) {
            return null;
        }
        T res = (T) attributeMap.get(name);
        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String name, int index) {
        if (name == null) {
            return null;
        }
        T obj = (T) attributeMap.get(name);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            throw new IllegalArgumentException("Attribute " + name + " is not a list");
        }
        return ((List<T>) obj).get(index);

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void add(String name, T value) {
        if (name == null || name.isEmpty() || value == null) {
            return;
        }
        List<T> list = null;
        Object obj = attributeMap.get(name);
        if (obj == null) {
            list = new ArrayList<>();
            attributeMap.put(name, list);
        } else if (obj instanceof List) {
            list = (List<T>) obj;
        } else {
            throw new IllegalArgumentException("Attribute " + name + " is not a list");
        }
        list.add(value);
    }

    @SuppressWarnings("unchecked")
    public static Object getFirstAppearence(Entity entity, String name) {
        if (entity == null || name == null) {
            return null;
        }

        Map<String, Object> keyvalues = ((SimpleEntity) entity).getAttributeMap();
        if (CollectionUtils.contains(keyvalues.keySet().iterator(), name)) {
            return entity.get(name);
        }
        Set<String> names = keyvalues.keySet();
        for (String n : names) {
            Object obj = entity.get(n);
            if (isNonEmptyEntityList(obj)) {
                continue;
            }
            List<Entity> el = (List<Entity>) obj;
            for (Entity e : el) {
                Object o = getFirstAppearence(e, name);
                if (o != null) {
                    return o;
                }
            }
        }
        return null;
    }

    protected static boolean isNonEmptyEntityList(Object obj) {
        if (null == obj || !(obj instanceof List<?>)) {
            return false;
        }
        List<?> list = (List<?>) obj;
        return (!CollectionUtils.isEmpty(list) && list.get(0) instanceof Entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getByPath(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }

        String[] nodes = path.split(PATH_DELIM, -1);
        if (nodes == null || nodes.length == 0) {
            return null;
        }

        int count = nodes.length;
        Entity entity = this;
        for (int i = 0; i < count; i++) {
            Object value = getByPathNode(entity, nodes[i]);
            if (i == count - 1) {
                return value;
            }
            if (value instanceof List<?>) {
                log.info("No list index specified for " + nodes[i]);
            }
            if (value == null || !(value instanceof Entity)) {
                return null;
            }
            entity = (Entity) value;
        }
        return null;
    }

    protected Object getByPathNode(Entity entity, String node) {
        if (entity == null || node == null) {
            return null;
        }
        String elementName = node;
        String indexStr = null;
        int pos = node.indexOf(INDEX_DELIM_START);
        if (pos != -1) {
            elementName = node.substring(0, pos);
            int pos2 = node.indexOf(INDEX_DELIM_END);
            if (pos2 != -1) {
                indexStr = node.substring(pos + 1, pos2).trim();
            } else {
                indexStr = node.substring(pos + 1).trim();
            }
        }
        Object value = entity.get(elementName.trim());
        if (value instanceof List<?> && indexStr != null && !indexStr.isEmpty()) {
            try {
                int index = Integer.parseInt(indexStr);
                value = ((List<?>) value).get(index);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid index " + indexStr + " for " + node, e);
            }
        }
        return value;
    }

    @Override
    public int size() {
        return (attributeMap != null ? attributeMap.size() : 0);
    }

    @Override
    public boolean equalsIgnoreCase(String key, String expectValue) {
        if (key == null || expectValue == null) {
            return false;
        }
        Object object = get(key);
        if (object == null) {
            return false;
        }
        if (object instanceof String) {
            return expectValue.equalsIgnoreCase(object.toString());
        }
        return false;
    }

    @Override
    public boolean equals(String key, String expectValue) {
        if (key == null || expectValue == null) {
            return false;
        }
        Object object = get(key);
        if (object == null) {
            return false;
        }
        if (object instanceof String) {
            return expectValue.equals(object.toString());
        }
        return false;
    }

    @Override
    public boolean isEmpty(String key) {
        Object object = get(key);
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return StringUtils.isBlank((String) object);
        }
        return false;
    }

    @Override
    public String toString() {
        return "SimpleEntity [attributeMap=" + attributeMap + ", entityName=" + entityName + ", parent=" + parent + "]";
    }



}
