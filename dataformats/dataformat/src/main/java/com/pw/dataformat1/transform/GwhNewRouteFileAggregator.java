package com.pw.dataformat1.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;

public class GwhNewRouteFileAggregator extends GwhAbstractMapAggregationStrategy<String, Object> {

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getMap(Exchange exchange) {
        return exchange.getIn().getBody(Map.class);
    }

    @Override
    public void addMap(Map<String, List<Object>> currentMap, Map<String, Object> newMap) {
        newMap.forEach((k, v) -> {
                currentMap.computeIfAbsent(k, key -> new ArrayList<>()).add(v);
        });
    }

    @Override
    public void addMetadata(Map<String, Object> metadata, Exchange exchange) {
        metadata.put("sourceName", exchange.getIn().getHeader(Exchange.FILE_NAME, String.class));
        metadata.put("sourceType", "file");
        // metadata.put("batchNo", exchange.getProperty(Exchange.A, null)
        metadata.put("totalCount", (exchange.getProperty(Exchange.SPLIT_INDEX, Integer.class) + 1));
        metadata.put("lastBatch", exchange.getProperty(Exchange.SPLIT_COMPLETE));
    }

    public void addGroupMetadata(Map<String, Object> metadata, Map<String, List<Object>> currentMap, Exchange exchange) {
        metadata.put("batchCount", exchange.getProperty(Exchange.AGGREGATED_SIZE, Integer.class));
        List<Object> metaDataMap = new ArrayList<>();
        metaDataMap.add(metadata);
        currentMap.put("metadata", metaDataMap);
    }

}
