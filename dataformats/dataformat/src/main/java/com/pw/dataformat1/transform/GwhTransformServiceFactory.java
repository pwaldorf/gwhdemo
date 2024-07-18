package com.pw.dataformat1.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;


@Component
public class GwhTransformServiceFactory {

    public static Map<String, GwhTransformService> gwhTransformServiceCache = new HashMap<>();

    public final List<GwhTransformService> gwhTransformServices;

    public GwhTransformServiceFactory(List<GwhTransformService> gwhTransformServices) {
        this.gwhTransformServices = gwhTransformServices;
    }

    @PostConstruct
    void initCache() {
        gwhTransformServices.forEach(gwhTransformService -> gwhTransformServiceCache.put(
            gwhTransformService.getName(), gwhTransformService
        ));
    }

    public GwhTransformService getGwhTransformService(String type) {
        return gwhTransformServiceCache.get(type);
    }
}
