package com.pw.support1.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GwhNewRouteFileParser {

    private Integer headerRC = 1;

    private GwhTransformService gwhTransformService;

    public void process(Exchange exchange) {

        var message = exchange.getIn().getBody(String.class);
        var currentMessageCount = exchange.getProperty(Exchange.SPLIT_INDEX, Integer.class);
        var splitComplete = exchange.getProperty(Exchange.SPLIT_COMPLETE, Boolean.class);

        Map<String, Object> map = new HashMap<>();
        if (currentMessageCount < headerRC) {
            map.put("header", message);

        } else if (splitComplete) {
            map.put("footer", message);

        } else {
            GwhTransformService transformService = getTransformService("FIX");
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

    private GwhTransformService getTransformService(String format) {

        if (gwhTransformService != null) {
            return gwhTransformService;
        }

        switch (StringUtils.upperCase(format)) {
            case "FIX":
                gwhTransformService = new GwhFixedTransformService();
                break;
            case "DELIMITER":
                gwhTransformService = new GwhDelimitedTransformService();
                break;
            // default:
            //     gwhTransformService = new GwhDefaultTransformService();
            //     break;
        }
        return gwhTransformService;
    }
}