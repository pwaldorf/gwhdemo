package com.pw.gwhparser.parser.util;

import javax.xml.stream.XMLEventReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceUtil {

    private ResourceUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void release(Object object) {
        try {
            if (object instanceof AutoCloseable) {
                ((AutoCloseable) object).close();
            } else if (object instanceof XMLEventReader) {
                ((XMLEventReader) object).close();
            }
        } catch (Exception e) {
            log.info("Resource release error: " + e);
        }
    }
}
