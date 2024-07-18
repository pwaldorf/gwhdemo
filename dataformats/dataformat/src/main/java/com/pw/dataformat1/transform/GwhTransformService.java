package com.pw.dataformat1.transform;

import java.util.Map;

public interface GwhTransformService<K, V> {
    public String getName();
    public Map<K, V> process(String rawMessage, String template, Boolean isHeader) throws Exception;
}
