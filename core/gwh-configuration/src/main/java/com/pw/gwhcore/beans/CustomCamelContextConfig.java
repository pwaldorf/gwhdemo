package com.pw.gwhcore.beans;

import com.pw.gwhcore.gwhcaffeinecache.GwhCaffeineCacheBuilder;
import com.pw.gwhcore.gwhroutes.GwhRouteBuilder;
import com.pw.support.configuration.GwhBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pw.gwhcore.gwhcaffeinecache.GwhCacheLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class CustomCamelContextConfig {

    private final ApplicationContext applicationContext;
    private final GwhRouteBuilder gwhRoutesBuilder;

    private final GwhCaffeineCacheBuilder gwhCacheLoader;

    public CustomCamelContextConfig(ApplicationContext applicationContext, GwhRouteBuilder gwhRoutesBuilder, GwhCaffeineCacheBuilder gwhCacheLoader) {
        this.applicationContext = applicationContext;
        this.gwhRoutesBuilder = gwhRoutesBuilder;
        this.gwhCacheLoader = gwhCacheLoader;
    }

    @Bean
    public CamelContextConfiguration camelContextConfiguration() {

        //String newLine = System.getProperty("line.separator");

        return new CamelContextConfiguration() {

            @Override
            public void beforeApplicationStart(CamelContext camelContext) {

                applicationContext.getBeansOfType(GwhBuilder.class).forEach((beanName, bean) -> {
                    System.out.println("PJWB: " + beanName);
                    bean.build();
                });
//
//                log.info("Adding new routes");
//                gwhRoutesBuilder.build();
//
//                log.info("Adding new caches");
//                gwhCacheLoader.build();

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
