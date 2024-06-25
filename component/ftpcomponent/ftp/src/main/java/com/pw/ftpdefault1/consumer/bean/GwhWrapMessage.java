package com.pw.ftpdefault1.consumer.bean;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class GwhWrapMessage {

    private final String FILE_NAME = "\"fileName\":";
    private final String BATCH_NUMBER = "\"batchNo\":";
    private final String BATCH_COUNT = "\"batchCount\":";
    private final String TOTAL_COUNT = "\"totalCount\":";
    private final String LAST_BATCH = "\"lastBatch\":";

    private String fileName;
    private int batchNumber = 0;
    private int totalMessageCount = 0;
    private int batchCount = 0;
    private boolean lastBatch = false;

    public void wrapMessage(Exchange exchange) throws Exception {


        fileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
        totalMessageCount = exchange.getProperty(Exchange.AGGREGATED_SIZE, Integer.class);
        batchNumber = exchange.getProperty(Exchange.SPLIT_INDEX, Integer.class) + 1;
        batchCount = exchange.getIn().getHeader("GWHBatchCount", Integer.class) + 1;
        lastBatch = exchange.getProperty(Exchange.SPLIT_COMPLETE, Boolean.class);

        StringBuilder newBody = new StringBuilder();
        newBody.append("{\"header\": { " );
        newBody.append(FILE_NAME + " \"" + fileName + "\"");
        newBody.append(",");
        newBody.append(BATCH_NUMBER + " " + batchNumber);
        newBody.append(",");
        newBody.append(BATCH_COUNT + " " + batchCount);
        newBody.append(",");
        newBody.append(TOTAL_COUNT + " " + totalMessageCount);
        newBody.append(",");
        newBody.append(LAST_BATCH + " \"" + lastBatch + "\"");
        newBody.append("}");
        newBody.append(",");
        newBody.append("\"message\": " );
        newBody.append(exchange.getIn().getBody());
        newBody.append("}");

        exchange.getIn().setBody(newBody.toString());
    }

}
