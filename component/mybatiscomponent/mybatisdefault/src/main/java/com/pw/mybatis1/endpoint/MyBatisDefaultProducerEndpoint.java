package com.pw.mybatis1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.mybatis;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.dsl.MyBatisEndpointBuilderFactory.MyBatisEndpointProducerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.support1.route.GwhEndpointProducerBuilder;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.mybatis.default1.producer.enabled", havingValue = "true", matchIfMissing = false)
public class MyBatisDefaultProducerEndpoint implements GwhEndpointProducerBuilder {

    @Override
    public EndpointProducerBuilder getProducerEndpoint() {
        MyBatisEndpointProducerBuilder myBatisEndpointProducerBuilder =
                mybatis("myBatisDefaultProducer", "{{statementName}}");

        return myBatisEndpointProducerBuilder;
    }

}
