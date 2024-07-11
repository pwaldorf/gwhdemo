package com.pw.dataformat1.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public abstract class GwhAbstractMapAggregationStrategy<K, V> implements AggregationStrategy {

    public abstract Map<K, V> getMap(Exchange exchange);
    public abstract void addMap(Map<K, List<V>> currentMap, Map<K, V> newMap);
    public abstract void addMetadata(Map<String, Object> currentMetadata, Exchange exchange);
    public abstract void addGroupMetadata(Map<String, Object> currentMetadata, Map<K, List<V>> currentMap, Exchange exchange);

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        Map<K, List<V>> map;
        Map<String, Object> metadata;
        if (oldExchange == null) {
            map = getCurrentMap(newExchange);
            metadata = getCurrentMetadata(newExchange);
        } else {
            map = getCurrentMap(oldExchange);
            metadata = getCurrentMetadata(oldExchange);
        }

        if (newExchange != null) {
            addMap(map, getMap(newExchange));
            addMetadata(metadata, newExchange);
        }

        return oldExchange != null ? oldExchange : newExchange;
    }

    @SuppressWarnings("unchecked")
    private Map<K, List<V>> getCurrentMap(Exchange exchange) {

        Map<K, List<V>> map = exchange.getProperty("GwhExchangeMap", Map.class);

        if (map == null) {
            map = new HashMap<>();
            exchange.setProperty("GwhExchangeMap", map);
        }
        return map;
    }

    private Map<String, Object> getCurrentMetadata(Exchange exchange) {

        Map<String, Object> map = exchange.getProperty("GwhExchangeMetadata", Map.class);

        if (map == null) {
            map = new HashMap<>();
            exchange.setProperty("GwhExchangeMetadata", map);
        }
        return map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCompletion(Exchange exchange) {
        if (exchange != null) {

            Map<K, List<V>> map = (Map<K, List<V>>) exchange.removeProperty("GwhExchangeMap");
            Map<String, Object> me = (Map<String, Object>) exchange.removeProperty("GwhExchangeMetadata");
            if (map != null) {
                addGroupMetadata(me, map, exchange);
                exchange.getIn().setBody(map);
            }
        }
    }

}
