package com.pw.gwhcore1.gwhproperties;

import com.pw.api1.configuration.GwhProperty;
import com.pw.api1.configuration.GwhPropertyFactory;
import org.springframework.stereotype.Component;

//@Component
public class GwhDefaultPropertyFactory implements GwhPropertyFactory {
    @Override
    public GwhProperty createProperty(String key, String value) {
        return new GwhDefaultProperty(key, value);
    }
}
