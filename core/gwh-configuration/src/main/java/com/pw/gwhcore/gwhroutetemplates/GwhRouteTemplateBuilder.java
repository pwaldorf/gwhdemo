package com.pw.gwhcore.gwhroutetemplates;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.apache.camel.spi.RouteTemplateParameterSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateBuilder implements RouteTemplateParameterSource {

    @SuppressWarnings("unused")
    private final String TEMPLATE_ID = "templateId";
    @SuppressWarnings("unused")
    private final String LOCATION = "location";

    private final Map<String, Map<String, Object>> parameters = new LinkedHashMap<>();

    public GwhRouteTemplateBuilder(GwhRouteTemplates gwhRouteTemplates) {

        log.debug("Adding new routes from template params");
        gwhRouteTemplates.getAllTemplates().stream().forEach(routeTemplate -> {
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
