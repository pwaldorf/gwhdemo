package com.pw.gwhcore1.gwhroutes;

import com.pw.api1.configuration.GwhRoute;
import com.pw.api1.GwhBuilder;
import com.pw.api1.GwhProfileResource;
import com.pw.gwhcore1.GwhConfigurationProperties;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@ConditionalOnProperty(value = "gwh.framework.load.routes.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteBuilder implements GwhBuilder {

    private GwhProfileResource<GwhRoute> gwhResource;

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final CamelContext camelContext;

    public GwhRouteBuilder(GwhConfigurationProperties gwhConfigurationProperties, CamelContext camelContext) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.camelContext = camelContext;
    }

    public void build() {

        if (gwhResource == null) {
            return;
        }

        List<Resource> resourceList = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        gwhResource.getResource(gwhConfigurationProperties.getProfile(),
                                gwhConfigurationProperties.getRegion(),
                                gwhConfigurationProperties.getVersion())
            .forEach(routeEntity -> resourceList.add(ResourceHelper.fromString(
                (routeEntity.getRouteType().equalsIgnoreCase("java")
                  ? getRouteClassName(routeEntity.getRoute()) + "." + routeEntity.getRouteType().toLowerCase().trim()
                  : "MyRoute" + atomicInteger.incrementAndGet() + "." + routeEntity.getRouteType().toLowerCase().trim()),
                routeEntity.getRoute())));

        try {
            ExtendedCamelContext extendedCamelContext = camelContext.adapt(ExtendedCamelContext.class);
            extendedCamelContext.getRoutesLoader().loadRoutes(resourceList);
            // PluginHelper.getRoutesLoader(camelContext.getCamelContextExtension()).loadRoutes(resourceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setGwhResource(GwhProfileResource<GwhRoute> gwhResource) {
        this.gwhResource = gwhResource;
    }

    private String getRouteClassName(String javaRoute) {
        // Regular expression to match the class name
        String classPattern = "public\\s+class\\s+(\\w+)";

        // Compile the regex and create a matcher for the code
        Pattern pattern = Pattern.compile(classPattern);
        Matcher matcher = pattern.matcher(javaRoute);

        // Find and print the class name
        if (matcher.find()) {
            String className = matcher.group(1);  // Group 1 is the class name
            log.info("Class name: {}", className);
            return className;
        } else {
            throw new RuntimeException("Class name not found in the route");
        }
    }
}
