package com.pw.support1.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class GwhNewRouteFileFormatter {

    private static final String HEADER = "header";
    private static final String FOOTER = "footer";
    private static final String MESSAGE = "message";
    private static final String MESSAGES = "messages";
    private static final String HEADER_CONTENT = "headerContent";
    private static final String FOOTER_CONTENT = "footerContent";

    public void process(Exchange exchange) {

        Map<String, List<Object>> message = exchange.getIn().getBody(Map.class);

        Map<String, Object> messageOut = new HashMap<>();

        messageOut.put(HEADER, getHeader(message));
        messageOut.put(MESSAGES, message.get(MESSAGE));

        exchange.getIn().setBody(messageOut, Map.class);
    }

    private Map<String, Object> getHeader(Map<String, List<Object>> message) {

        Map<String, Object> metadata = (Map<String, Object>) message.get("metadata").get(0);

        if (metadata == null) {
            return new HashMap<String, Object>();
        }

        if (message.containsKey(HEADER)) {
            metadata.put(HEADER_CONTENT, getHeaderValues(message, HEADER));
        }

        if (message.containsKey(FOOTER)) {
            metadata.put(FOOTER_CONTENT, getFooterValues(message, FOOTER));
        }

        return metadata;
    }

    public static String getHeaderValues(Map<String, List<Object>> map, String key) {
        List<Object> values = map.get(key);
        if (values == null) {
            return "";
        }

        // Join values with newline delimiter
        return String.join("\n", values.stream().map(Object::toString).toArray(String[]::new));
    }

    public static String getFooterValues(Map<String, List<Object>> map, String key) {
        List<Object> values = map.get(key);
        if (values == null) {
            return "";
        }

        // Join values with newline delimiter
        return String.join("\n", values.stream().map(Object::toString).toArray(String[]::new));
    }
}
