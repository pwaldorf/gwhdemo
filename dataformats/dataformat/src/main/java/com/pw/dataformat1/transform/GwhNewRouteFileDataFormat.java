package com.pw.dataformat1.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class GwhNewRouteFileDataFormat {

    private static final String HEADER = "header";
    private static final String FOOTER = "footer";
    private static final String MESSAGE = "message";
    private static final String MESSAGES = "messages";
    private static final String HEADER_CONTENT = "headerContent";
    private static final String FOOTER_CONTENT = "footerContent";

    private Integer headerRC = 1;

    private final GwhTransformServiceFactory gwhTransformServiceFactory;

    public GwhNewRouteFileDataFormat(GwhTransformServiceFactory gwhTransformServiceFactory) {
        this.gwhTransformServiceFactory = gwhTransformServiceFactory;
    }

    public void parse(Exchange exchange) {

        var message = exchange.getIn().getBody(String.class);
        var currentMessageCount = exchange.getProperty(Exchange.SPLIT_INDEX, Integer.class);
        var splitComplete = exchange.getProperty(Exchange.SPLIT_COMPLETE, Boolean.class);

        Map<String, Object> map = new HashMap<>();
        if (currentMessageCount < headerRC) {
            map.put("header", message);

        } else if (splitComplete) {
            map.put("footer", message);

        } else {
            GwhTransformService transformService = gwhTransformServiceFactory.getGwhTransformService("FIXED");
            try {
                map.put("message", transformService.process(message,
                                                    "{content:'10,21,16,16,16',groupNum:40,headerRowCount:1,footerRowCount:1}",
                                            false));

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        exchange.getIn().setBody(map, Map.class);
    }

    // private GwhTransformService getTransformService(String format) {

    //     if (gwhTransformService != null) {
    //         return gwhTransformService;
    //     }

    //     switch (StringUtils.upperCase(format)) {
    //         case "FIX":
    //             gwhTransformService = new GwhFixedTransformService();
    //             break;
    //         case "DELIMITER":
    //             gwhTransformService = new GwhDelimitedTransformService();
    //             break;
    //         // default:
    //         //     gwhTransformService = new GwhDefaultTransformService();
    //         //     break;
    //     }
    //     return gwhTransformService;
    // }

    public void format(Exchange exchange) {

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
