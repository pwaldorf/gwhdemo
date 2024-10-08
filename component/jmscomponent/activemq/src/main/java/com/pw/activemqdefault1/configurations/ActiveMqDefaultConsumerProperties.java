package com.pw.activemqdefault1.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.activemq.default1.consumer")
public class ActiveMqDefaultConsumerProperties {

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

    private String defaultRoutePolicy = "activeMqConsumer";
    private String defaultRouteConfigurationPattern = ".*activeMqConsumerError.*,.*activeMqConsumerConfig.*";
    private String defaultConsumerEndpoint = "activeMqConsumerEndpoint";

}