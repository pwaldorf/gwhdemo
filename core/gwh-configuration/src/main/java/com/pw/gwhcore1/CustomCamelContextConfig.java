package com.pw.gwhcore1;

import com.pw.api1.GwhBuilder;
import com.pw.api1.GwhLoader;
import com.pw.support1.util.ApplicationContextProvider;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CustomCamelContextConfig {


    @Bean
    public CamelContextConfiguration camelContextConfiguration() {

        return new CamelContextConfiguration() {

            @Override
            public void beforeApplicationStart(CamelContext camelContext) {

                ApplicationContextProvider.getApplicationContext().getBeansOfType(GwhBuilder.class).forEach((beanName, bean) -> {
                    log.info("Building {}", beanName);
                    bean.build();
                });

            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                //get routes and update route properties
                //camelContext.getRoute("test_message_route").getProperties().put("test1", "Test2");
                ApplicationContextProvider.getApplicationContext().getBeansOfType(GwhLoader.class).forEach((beanName, bean) -> {
                    log.info("Building {}", beanName);
                    bean.load();
                });
            }
        };
    }
}