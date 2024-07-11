package com.pw.dataformat1.transform;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GwhTransformServiceFactory {

    private final Map<String, GwhTransformService> gwhTransformServices;

    public GwhTransformService getGwhTransformService(String type) {
        return gwhTransformServices.get(type);
    }
}
