package com.pw.mybatis1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.mybatis;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.MyBatisEndpointBuilderFactory.MyBatisEndpointConsumerBuilder;

import com.pw.support1.route.GwhEndpointConsumerBuilder;

public class MyBatisDefaultConsumerEndpoint implements GwhEndpointConsumerBuilder {

    @Override
    public EndpointConsumerBuilder getConsumerEndpoint() {
        MyBatisEndpointConsumerBuilder myBatisEndpointConsumerBuilder =
                mybatis("myBatisDefaultConsumer", "{{statementName}}");

        return myBatisEndpointConsumerBuilder;
    }


}
