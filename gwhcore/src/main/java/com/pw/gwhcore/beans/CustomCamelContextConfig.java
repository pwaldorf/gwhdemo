package com.pw.gwhcore.beans;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class CustomCamelContextConfig {

    @Autowired
    GwhRoutesLoader gwhRoutesLoader;

    @Autowired
    GwhCacheLoader gwhCacheLoader;

    @Bean
    public CamelContextConfiguration camelContextConfiguration() {

        //String newLine = System.getProperty("line.separator");

        return new CamelContextConfiguration() {

            @Override
            public void beforeApplicationStart(CamelContext camelContext) {

                log.info("Adding new routes");
                gwhRoutesLoader.loadRoutes();

                log.info("Adding new caches");
                gwhCacheLoader.load();

                // // Load YAML or XML DSL Route from String.
                // try {

                //     camelContext.addRoutes(new RouteBuilder() {

                //         @Override
                //         public void configure() throws Exception {
                //             Resource resource = ResourceHelper.fromString("MyRoute.xml", newRoute);
                //             PluginHelper.getRoutesLoader(camelContext.getCamelContextExtension()).loadRoutes(resource);
                //         }
                //     });
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {

            }

        };
    }

}
