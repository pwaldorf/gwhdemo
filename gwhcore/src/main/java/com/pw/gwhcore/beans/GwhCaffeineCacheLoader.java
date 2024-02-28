package com.pw.gwhcore.beans;


import org.apache.camel.CamelContext;
import org.apache.camel.component.caffeine.CaffeineConfiguration;
import org.apache.camel.component.caffeine.EvictionType;
import org.apache.camel.component.caffeine.load.CaffeineLoadCacheComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.github.benmanes.caffeine.cache.CacheLoader;

import java.util.Map;
import java.util.HashMap;

@Component
public class GwhCaffeineCacheLoader implements GwhCacheLoader {

    @Autowired
    CamelContext camelContext;

    @Autowired
    ApplicationContext context;

    @Override
    public void load() {

        Map<String, CaffeineConfiguration> configs = cacheConfigs();
        
        configs.entrySet().stream().forEach(config -> {
            camelContext.getRegistry().bind(config.getKey(), new CaffeineLoadCacheComponent() {
                {
                    setConfiguration(config.getValue());
                }
            });
        });
    }

    public Map<String, CaffeineConfiguration> cacheConfigs() {
        Map<String, CaffeineConfiguration> configs = new HashMap<>();
                
        CaffeineConfiguration configuration = new CaffeineConfiguration();
        configuration.setStatsEnabled(true);
        configuration.setEvictionType(EvictionType.SIZE_BASED);
        configuration.setInitialCapacity(1000);
        configuration.setMaximumSize(100000);
        //configuration.setCacheLoader(context.getBean("gwhRouteTemplateCacheLoader", CacheLoader.class));
        configs.put("gwhRouteTemplateCache", configuration);
        
        return configs;
    }

}
