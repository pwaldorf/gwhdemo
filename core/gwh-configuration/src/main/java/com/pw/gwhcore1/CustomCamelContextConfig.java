package com.pw.gwhcore1;

import com.pw.api1.GwhBuilder;
import com.pw.api1.GwhLoader;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CustomCamelContextConfig {

    private final ApplicationContext applicationContext;

    public CustomCamelContextConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public CamelContextConfiguration camelContextConfiguration() {

        return new CamelContextConfiguration() {

            @Override
            public void beforeApplicationStart(CamelContext camelContext) {

                applicationContext.getBeansOfType(GwhBuilder.class).forEach((beanName, bean) -> {
                    System.out.println("Building " + beanName);
                    bean.build();
                });

            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                //get routes and update route properties
                //camelContext.getRoute("test_message_route").getProperties().put("test1", "Test2");
                applicationContext.getBeansOfType(GwhLoader.class).forEach((beanName, bean) -> {
                    System.out.println("Building " + beanName);
                    bean.load();
                });
            }
        };
    }
}