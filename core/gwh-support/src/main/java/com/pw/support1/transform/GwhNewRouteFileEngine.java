package com.pw.support1.transform;

import java.util.ArrayList;
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

import java.util.Arrays;

// @Slf4j
// @Component
public class GwhNewRouteFileEngine {

    private static final String HEADER = "header";
    private static final String MESSAGES = "messages";
    private static final String HEADER_CONTENT = "headerContent";
    private static final String FOOTER_CONTENT = "footerContent";

    private String delimiter = "\n";

    String[] previousMessages = new String[]{};

    GwhTransformService gwhTransformService;

    public void process(Exchange exchange) {

        String fileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);

        Integer headerRC = 1;
        Integer footerRC = 1;
        String headerContent = StringUtils.EMPTY;
        String footerContent = StringUtils.EMPTY;
        String body = exchange.getIn().getBody(String.class);
        Integer msgStarter = 0;
        long totalCount = 0;

        Map<String, Object> m = new HashMap<>();
        Map<String, Object> header = new HashMap<>();
        List<Object> objs = new ArrayList<>();
        String[] messages = body.split(delimiter);
        Integer i = 0;
        Boolean splitCompleted = true;
        long batchCount = messages.length;
        long curCount = batchCount;

        if (i == 0) {
            headerContent = loadHeaderContent(messages, headerRC);
            msgStarter = headerRC;
            curCount = curCount - headerRC;
        }

        if (splitCompleted) {
            footerContent = loadFooterContent(messages, footerRC);
            previousMessages = new String[]{};
            curCount = curCount - footerRC;
        } else {
            previousMessages = messages;
        }

        if (splitCompleted) {
            batchCount = batchCount - footerRC;
        }

        for (int j = msgStarter; j < batchCount; j++) {
            String message = messages[j];
            GwhTransformService transformService = getTransformService("FIX");
            Object newMsg;
            try {
                newMsg = transformService.process(message,
                        "{content:'10,21,16,16,16',groupNum:40,headerRowCount:1,footerRowCount:1}",
                        isFirstMessage(i, j));

                if (!ObjectUtils.isEmpty(newMsg)) {
                    objs.add(newMsg);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        totalCount = totalCount + curCount;
        header = (Map<String, Object>) genMetadataMsg(fileName, curCount, Long.valueOf(i + 1),
                totalCount, splitCompleted, headerContent, footerContent);
        String tmsStr = "";

        m.put(HEADER, header);
        m.put(MESSAGES, objs);
        // tmsStr = GsonUtils.getGson().toJson(m);
        // exchange.getIn().setBody(tmsStr);
        exchange.getIn().setBody(m);

    }

    private String loadHeaderContent(String[] messages, Integer headerRC) {

        if(messages.length < headerRC) {
            return StringUtils.join(messages, delimiter);
        }

        List<String> headerContent = new ArrayList<>();
        for(int i = 0; i < headerRC; i++) {
            headerContent.add(messages[i]);
        }
        return StringUtils.join(headerContent, delimiter);
    }

    private String loadFooterContent(String[] messages, Integer footerRC) {

        if(messages.length < footerRC) {
            Queue<String> footer = new LinkedList<>();
            if (ArrayUtils.isEmpty(previousMessages)) {
                return StringUtils.join(messages, delimiter);
            }
            int previousFootStart = previousMessages.length - footerRC + messages.length;
            for (int i = previousFootStart; i < previousMessages.length; i++) {
                footer.add(previousMessages[i]);
            }
            footer.addAll(Arrays.asList(messages));
            return StringUtils.join(footer, delimiter);
        }

        List<String> footerContent = new LinkedList<>();
        for(int i = messages.length - footerRC; i < messages.length; i++) {
            footerContent.add(messages[i]);
        }
        return StringUtils.join(footerContent, delimiter);
    }

    private Boolean isFirstMessage(Integer i, Integer j) {
        if (i == 0 && j == 0) {
            return true;
        }
        return false;
    }

    private Object genMetadataMsg(String name, long count, long batchNum, long totalCount, Boolean lastBatch,
                                    String headerContent, String footerContent) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("sourceName", name);
        metadata.put("sourceType", "file");
        metadata.put("batchCount", String.valueOf(count));
        metadata.put("batchNo", String.valueOf(batchNum));
        metadata.put("totalCount", String.valueOf(totalCount));
        metadata.put("lastBatch", lastBatch);
        if (StringUtils.isNotBlank(headerContent)) {
            metadata.put(HEADER_CONTENT, headerContent);
        }
        if (StringUtils.isNotBlank(footerContent)) {
            metadata.put(FOOTER_CONTENT, footerContent);
        }
        return metadata;
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