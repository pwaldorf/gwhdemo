package com.pw.gwhparser.parser.model;

import com.pw.gwhparser.parser.enums.DataType;

// Data Dictionary Element
public class Element {

    private String name;
    private String description;
    private DataType type;
    private String edgmid;

    public Element() {
    }

    public Element(String name, String description, DataType type, String edgmid) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.edgmid = edgmid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public String getEdgmid() {
        return edgmid;
    }

    public void setEdgmid(String edgmid) {
        this.edgmid = edgmid;
    }

    @Override
    public String toString() {
        return "Element [name=" + name + ", description=" + description + ", type=" + type + ", edgmid=" + edgmid + "]";
    }

}
