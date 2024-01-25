package com.pw.gwhcore.beans;

import java.util.ArrayList;
import java.util.List;
import org.apache.camel.CamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.support.PluginHelper;
import org.apache.camel.support.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.pw.gwhcore.jpa.model.GwhRouteEntity;
import com.pw.gwhcore.jpa.service.GwhRouteService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CustomCamelContextConfig {

    @Autowired
    GwhRouteService gwhRouteService;

    @Bean
    public CamelContextConfiguration camelContextConfiguration() {

        //String newLine = System.getProperty("line.separator");
        
        // XML DSL Example
        // String newRoute = new StringBuilder("<route id=\"testdata\">")
        //     .append("<from uri=\"timer://foo?period=5000\"></from>")
        //     .append("<setBody>")
        //     .append(    "<constant>Hello My XML World</constant>")
        //     .append("</setBody>")
        //     .append("<log message=\"Body ${body}\" />")
        //     .append("<to uri=\"jmsProducerTransacted:queue:mailbox\"/>")
        //     .append("<log message=\"Sent JMS Test Message\" />")
        //     .append("</route>")
        //     .toString();
                

        return new CamelContextConfiguration() {

            @Override
            public void beforeApplicationStart(CamelContext camelContext) {

                log.debug("Adding new routes");

                List<Resource> resourceList = new ArrayList<>();

                List<GwhRouteEntity> routeEntities = gwhRouteService.getByProfile("dispatcher");
                routeEntities.stream().forEach(routeEntity -> resourceList.add(ResourceHelper.fromString("MyRoute.xml", routeEntity.getRoute())));

                try {
                    PluginHelper.getRoutesLoader(camelContext.getCamelContextExtension()).loadRoutes(resourceList);
                } catch (Exception e) {                    
                    e.printStackTrace();
                }
                
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
