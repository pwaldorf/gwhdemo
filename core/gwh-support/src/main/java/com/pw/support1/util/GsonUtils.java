package com.pw.support1.util;

import lombok.extern.slf4j.Slf4j;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

@Slf4j
public class GsonUtils {

    private static final Gson mGson;

    static {
        mGson = new GsonBuilder().setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
    }

    public static Gson getGson() {
        return GsonUtils.mGson;
    }

    public static Map<String, Object> mapJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        Map<String, Object> mMap = null;
        try {
            Gson mGson = GsonUtils.mGson;
            mMap = mGson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            log.error("mapJson error", e);
        }
        return mMap;
    }
}
