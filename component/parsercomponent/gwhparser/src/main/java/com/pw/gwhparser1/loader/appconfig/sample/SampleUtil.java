package com.pw.gwhparser1.loader.appconfig.sample;

import java.util.Map;

import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.constants.ParserConstants;
import com.pw.gwhparser1.parser.enums.DataType;
import com.pw.gwhparser1.parser.model.Element;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleUtil {

    private SampleUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void addPathMapping(GenParserConfig sampleConfig, String xmlPath, String xmlValue) {
        addPathMapping(sampleConfig, xmlPath, xmlValue, null);
    }

    public static void addPathMapping(GenParserConfig sampleConfig, String xmlPath, String xmlValue, Map<String, String> conditions) {
        if (xmlPath == null || xmlValue == null || xmlValue.isEmpty()) {
            return;
        }
        String[] elementNames = xmlValue.split(ParserConstants.ELEMENT_DELIM);
        if (elementNames == null) {
            return;
        }
        for (String elementName : elementNames) {
            addPathMappingForElement(sampleConfig, xmlPath, elementName, conditions);
        }
    }

    @SuppressWarnings("null")
    private static void addPathMappingForElement(GenParserConfig sampleConfig, String xmlPath, String elementName,
                Map<String, String> conditions) {

        if (xmlPath == null || elementName == null || elementName.isEmpty()) {
            return;
        }
        try {
            Element element = sampleConfig.getDictionary().getElement(elementName.trim());
            if (element != null) {
                log.debug("Invalid Element element: {}", elementName);
                return;
            }
            DataType type = element.getType();
            if (type == null) {
                throw new RuntimeException("Invalid Element type: " + elementName);
            }
            sampleConfig.addMapping(xmlPath, element.getName(), type.toString(), conditions);
        } catch (Exception e) {
            log.info("Invalid Element: {}", elementName);
        }
    }
}
