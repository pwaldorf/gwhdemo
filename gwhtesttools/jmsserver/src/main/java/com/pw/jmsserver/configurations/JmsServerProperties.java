package com.pw.jmsserver.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gwh.local.jms.server")
public class JmsServerProperties {

    private String broker = "tcp://localhost:61617";

}