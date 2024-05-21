package com.pw.gwhparser.parser.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

// Fixedlength and CSV
@XmlRootElement(name="ElementConfig")
public class ElementConfig {

    @Expose
    private String name;

    @Expose
    private String type;

    @Expose
    private String format;

    @Expose
    private String path;

    @Expose
    private Integer start;

    @Expose
    private Integer length;

    @Expose
    private String defaultValue;

    @Expose
    private int index;

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "type")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "format")
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }

    @XmlElement(name = "path")
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    @XmlElement(name = "start")
    public Integer getStart() {
        return start;
    }
    public void setStart(Integer start) {
        this.start = start;
    }

    @XmlElement(name = "length")
    public Integer getLength() {
        return length;
    }
    public void setLength(Integer length) {
        this.length = length;
    }

    @XmlElement(name = "defaultValue")
    public String getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @XmlElement(name = "index")
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "ElementConfig [name=" + name + ", type=" + type + ", format=" + format + ", path=" + path + ", start="
                + start + ", length=" + length + ", defaultValue=" + defaultValue + ", index=" + index + "]";
    }


}
