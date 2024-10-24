package com.pw.activemq.gwhdefault.producer.default1.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.activemq.producer.default1")
public class ActiveMqDefaultProducerProperties {

    /**
     * JMS broker url
     */
    private String brokerUrl;

    /**
     * The Username to be used to autenticate with the JMS Broker
     */
    private String username;

    /**
     * The Password to be used to autenticate with the JMS Broker
     */
    private String password;

    /**
     * JMS Connection factory Cache Size
     */
    private int sessionCacheSize;

    private String defaultRoutePolicy = "activeMqProducer";
    private String defaultRouteConfigurationPattern = ".*activeMqProducerError.*,.*activeMqProducerConfig.*";
    private String defaultProducerEndpoint = "activeMqProducerEndpoint";

}