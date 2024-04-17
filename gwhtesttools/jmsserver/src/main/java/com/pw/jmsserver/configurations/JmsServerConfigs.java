package com.pw.jmsserver.configurations;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyAcceptorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("local")
public class JmsServerConfigs implements ArtemisConfigurationCustomizer {

    JmsServerProperties jmsServerProperties;

    public JmsServerConfigs(JmsServerProperties jmsServerProperties) {
        this.jmsServerProperties = jmsServerProperties;
    }

    @Override
    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
        try {
            configuration.addAcceptorConfiguration("netty", jmsServerProperties.getBroker());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        configuration.addConnectorConfiguration("nettyConnector", new TransportConfiguration(NettyConnectorFactory.class.getName()));
        configuration.addAcceptorConfiguration(new TransportConfiguration(NettyAcceptorFactory.class.getName()));
    }

}
