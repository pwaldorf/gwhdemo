package com.pw.activemq.gwhdefault.configurations.default1;

import org.springframework.core.InfrastructureProxy;
import org.springframework.jms.connection.CachingConnectionFactory;
import javax.jms.ConnectionFactory;

public class WrapCachingConnectionFactory extends CachingConnectionFactory implements InfrastructureProxy {

    @SuppressWarnings("null")
    @Override
    public Object getWrappedObject() {
        return this.getTargetConnectionFactory();
    }

    public WrapCachingConnectionFactory(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    public WrapCachingConnectionFactory() {

    }

}
