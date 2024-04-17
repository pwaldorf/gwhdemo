package com.pw.gwhparser.parser;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

import com.pw.gwhparser.parser.config.GenParserConfig;
import com.pw.gwhparser.parser.constants.ParserConstants;
import com.pw.gwhparser.parser.enums.ValueType;
import com.pw.gwhparser.parser.model.Entity;
import com.pw.gwhparser.parser.model.EntityFactory;
import com.pw.gwhparser.parser.model.PathMapping;
import com.pw.gwhparser.parser.util.ValueUtil;


public abstract class AbstractParser {

    protected String appKey;
    protected GenParserConfig config;
    protected EntityFactory entityFactory;
    protected Deque<EntityInfo> entityInfoDeque;
    protected List<PathMapping> pathMappings;
    protected StringBuilder tagPath;

    protected AbstractParser() {
        super();
    }

    protected AbstractParser(String appKey, GenParserConfig config, EntityFactory entityFactory) {
        if (config == null || entityFactory == null) {
            throw new IllegalArgumentException("config and entityFactory must not be null");
        }

        this.appKey = appKey;
        this.config = config;
        this.entityFactory = entityFactory;
    }

    protected boolean checkStartEntityForMappings() {
        if (pathMappings == null || pathMappings.isEmpty()) {
            return false;
        }
        boolean isStarted = false;
        Iterator<PathMapping> mappingsIterator = pathMappings.iterator();
        while (mappingsIterator.hasNext()) {
            PathMapping mapping = mappingsIterator.next();
            if (mapping == null) {
                continue;
            }
            ValueType type = mapping.getType();
            if (ValueType.ENTITY.equals(type) || ValueType.ENTITY_LIST.equals(type)) {
                startEntityForMapping(mapping.getElement(), type);
                isStarted = true;
            }
        }
        return isStarted;
    }

    protected void startEntityForMapping(String name, ValueType type) {
        Entity parentEntity = getEntity();
        if (name == null || parentEntity == null || entityFactory == null) {
            throw new IllegalArgumentException("name is required");
        }
        Entity subEntity = entityFactory.newSubEntity(name, parentEntity, appKey);
        if (ValueType.ENTITY_LIST.equals(type)) {
            parentEntity.add(name, subEntity);
        } else {
            parentEntity.set(name, subEntity);
        }
        startEntity(subEntity);
    }

    protected void setValue(Object valueObj) {
        setValue(pathMappings, valueObj);
    }

    protected void setValue(List<PathMapping> mappings, Object valueObj) {
        if (valueObj == null || mappings == null || mappings.isEmpty()) {
            return;
        }
        Entity entity = getEntity();
        if (entity == null) {
            throw new IllegalArgumentException("entity is required");
        }
        Iterator<PathMapping> mappingsIterator = mappings.iterator();
        while (mappingsIterator.hasNext()) {
            PathMapping mapping = mappingsIterator.next();
            if (mapping == null || !isValid(mapping)) {
                continue;
            }
            setValue(entity, mapping, valueObj);
        }
    }

    protected boolean isValid(PathMapping mapping) {
        return (mapping != null);
    }

    protected void setValue(Entity entity, PathMapping mapping, Object valueObj) {
        if (entity == null || mapping == null || valueObj == null) {
            return;
        }
        ValueType type = mapping.getType();
        if (ValueType.ENTITY.equals(type) || ValueType.ENTITY_LIST.equals(type)) {
            return;
        }
        ValueType simpleType = ValueUtil.getSimpleValueType(type);
        String name = mapping.getElement();
        Object value = convertSimpleValue(valueObj, simpleType);
        if (name == null || value == null) {
            return;
        }
        if (ValueUtil.isListValueType(type)) {
            entity.add(name, value);
        } else if (type == ValueType.STRING_CONCAT) {
            setStringConcat(entity, name, (String) value);
        } else {
            entity.set(name, value);
        }
    }

    protected void setStringConcat(Entity entity, String name, String str) {
        if (entity == null || name == null || str == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String currentValue = entity.get(name);
        if (currentValue != null && !currentValue.isEmpty()) {
            sb.append(currentValue);
            String delim = (config != null ? config.getStringConcatDelimiter() : null);
            sb.append(delim !=null ? delim : ParserConstants.BLANK);
        }
        sb.append(str);
        entity.set(name, sb.toString());
    }

    protected Object convertSimpleValue(Object valueObj, ValueType type) {
        if (valueObj == null || type == null) {
            return null;
        }
        return type.getValue(valueObj, config);
    }


    protected void startEntity(Entity entity) {
        if (entity == null) {
            throw new NullPointerException("entity is required");
        }
        String entityTagPath = (tagPath != null ? tagPath.toString() : ParserConstants.PATH_DELIM);
        pushEntityInfoDeque(new EntityInfo(entity, entityTagPath));
    }

    protected Entity getEntity() {
        EntityInfo info = peekEntityInfoDeque();
        return (info != null ? info.getEntity() : null);
    }

    protected boolean checkEndEntity() {
        String entityTagPath = (tagPath != null ? tagPath.toString() : ParserConstants.PATH_DELIM);
        EntityInfo entityInfo = peekEntityInfoDeque();
        if (entityTagPath != null && entityInfo != null && entityTagPath.equals(entityInfo.getTagPath())) {
            popEntityInfoDeque();
            return true;
        }
        return false;
    }

    protected EntityInfo peekEntityInfoDeque() {
        return (entityInfoDeque != null ? entityInfoDeque.peek() : null);
    }

    protected boolean popEntityInfoDeque() {
        if (entityInfoDeque != null && !entityInfoDeque.isEmpty()) {
            entityInfoDeque.pop();
            return true;
        }
        return false;
    }

    protected void pushEntityInfoDeque(EntityInfo entityInfo) {
        if (entityInfo == null) {
            throw new NullPointerException("entityInfoDeque is required");
        }
        if (entityInfoDeque == null) {
            entityInfoDeque = new LinkedList<>();
        }
        entityInfoDeque.push(entityInfo);
    }

    protected boolean hasPathMappings() {
        return pathMappings != null && !pathMappings.isEmpty();
    }

    protected void setMappingsForElement(String elementTagPath) {
        if (elementTagPath == null || elementTagPath.isEmpty() || config == null) {
            pathMappings = null;
        } else {
            pathMappings = config.getMappings(elementTagPath);
        }
    }

    protected String addTagPathLast(String tagPathLast) {
        if (tagPathLast == null || tagPathLast.isEmpty()) {
            throw new IllegalArgumentException("tagPathLast is required");
        }
        if (tagPath == null) {
            tagPath = new StringBuilder();
        }
        tagPath.append(ParserConstants.PATH_DELIM).append(tagPathLast);
        return tagPath.toString();
    }

    protected String getTagPathString() {
        return tagPath != null ? tagPath.toString() : "";
    }

    protected String removeTagPathLast() {
        if (tagPath != null && tagPath.length() > 0) {
            tagPath.delete(tagPath.lastIndexOf(ParserConstants.PATH_DELIM), tagPath.length());
            return tagPath.toString();
        }
        return ParserConstants.BLANK;
    }

//*********************************************************************************************** */
    public class  EntityInfo {

        private Entity entity;
        private String tagPath;

        public EntityInfo(Entity entity, String tagPath) {
            super();
            this.entity = entity;
            this.tagPath = tagPath;
        }

        public Entity getEntity() {
            return entity;
        }
        public String getTagPath() {
            return tagPath;
        }

        @Override
        public String toString() {
            return "EntityInfo [entity=" + entity + ", tagPath=" + tagPath + "]";
        }
    }
}
