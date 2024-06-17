package com.pw.gwhcore1.gwhproperties;

import com.pw.api1.configuration.GwhProperty;

public class GwhDefaultProperty implements GwhProperty {

    private String key;
    private String value;

    public GwhDefaultProperty(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
