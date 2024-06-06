package com.pw.jmsserver1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gwh.local.jms.server")
public class JmsServerProperties {

    private String brokerUrl = "tcp://localhost:61617";

}
