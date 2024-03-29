package com.pw.gwhcore.beans;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.camel.spi.RouteTemplateParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.pw.gwhcore.jpa.model.GwhRouteTemplateEntity;
import com.pw.gwhcore.jpa.service.GwhRouteTemplateService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class RouteTemplateBuilder implements RouteTemplateParameterSource {
    
    private final String TEMPLATE_ID = "templateId";    
    private final String LOCATION = "location";

    @Autowired
    @Qualifier("gwhRouteTemplateEntities")
    List<GwhRouteTemplateEntity> gwhRouteTemplateEntities;

    private final Map<String, Map<String, Object>> parameters = new LinkedHashMap<>();    

    //public RouteTemplateBuilder(GwhRouteTemplateService routeTemplateService) {
    public RouteTemplateBuilder(List<GwhRouteTemplateEntity> gwhRouteTemplateEntities) {        
        log.debug("Adding new routes from template params");                
        gwhRouteTemplateEntities.stream().forEach(routeTemplateEntity -> {
            parameters.computeIfAbsent(routeTemplateEntity.getRouteId(), 
                                        k -> new HashMap<>()).put(routeTemplateEntity.getTemplateParamName(), 
                                                                    routeTemplateEntity.getTemplateParamValue());                                 
        });
    }

    @Override
    public Map<String, Object> parameters(String routeId) {               
        return new HashMap<>(parameters.get(routeId));        
    }

    @Override
    public Set<String> routeIds() {
        return parameters.keySet();
    }
    
}
