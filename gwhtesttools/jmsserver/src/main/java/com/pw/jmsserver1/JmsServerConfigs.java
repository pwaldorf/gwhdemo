package com.pw.jmsserver1;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
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
            configuration.addAcceptorConfiguration("in-vm", "vm://0");
            configuration.addAcceptorConfiguration("tcp", jmsServerProperties.getBrokerUrl());
            configuration.addConnectorConfiguration("nettyConnector", new TransportConfiguration(NettyConnectorFactory.class.getName()));
            configuration.setJMXManagementEnabled(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
