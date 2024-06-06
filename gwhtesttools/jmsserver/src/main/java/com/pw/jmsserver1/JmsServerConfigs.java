package com.pw.jmsserver1;

import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("local")
public class JmsServerConfigs implements ArtemisConfigurationCustomizer {

    ArtemisProperties artemisProperties;

    JmsServerProperties jmsServerProperties;

    public JmsServerConfigs(JmsServerProperties jmsServerProperties, ArtemisProperties artemisProperties) {
        this.jmsServerProperties = jmsServerProperties;
        this.artemisProperties = artemisProperties;
    }

    @Override
    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
        try {
            log.info("brokerUrl: {}", jmsServerProperties.getBrokerUrl());
            configuration.addAcceptorConfiguration("netty", jmsServerProperties.getBrokerUrl());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // configuration.addConnectorConfiguration("nettyConnector", new TransportConfiguration(NettyConnectorFactory.class.getName()));
        // configuration.addAcceptorConfiguration(new TransportConfiguration(NettyAcceptorFactory.class.getName()));
    }

}
