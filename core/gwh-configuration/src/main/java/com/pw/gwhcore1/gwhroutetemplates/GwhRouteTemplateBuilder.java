package com.pw.gwhcore1.gwhroutetemplates;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.pw.support1.configuration.GwhResourceLoader;
import org.apache.camel.spi.RouteTemplateParameterSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.gwhcore1.model.GwhRouteTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateBuilder implements RouteTemplateParameterSource {

    @SuppressWarnings("unused")
    private final String TEMPLATE_ID = "templateId";
    @SuppressWarnings("unused")
    private final String LOCATION = "location";

    private final Map<String, Map<String, Object>> parameters = new LinkedHashMap<>();

    public GwhRouteTemplateBuilder(GwhResourceLoader<GwhRouteTemplate> gwhRouteTemplates) {

        log.debug("Adding new routes from template params");
        gwhRouteTemplates.getAll().stream().forEach(routeTemplate -> {
            parameters.computeIfAbsent(routeTemplate.getRouteId(),
                                        k -> new HashMap<>()).put(routeTemplate.getTemplateParamName(),
                                                                    routeTemplate.getTemplateParamValue());
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
