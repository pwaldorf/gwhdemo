package com.pw.support1.transform;

import java.util.Map;

public interface GwhTransformService<K, V> {
    public Map<K, V> process(String rawMessage, String template, Boolean isHeader) throws Exception;
}
