package com.pw.kafkadefault1.routeconfigurations;

import org.apache.camel.builder.RouteConfigurationBuilder;

import com.pw.kafkadefault1.beans.KafkaDefaultConsumerManualCommit;


public class KafkaManualCommitConfiguration extends RouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {
        routeConfiguration("kafkaConsumerManualCommit")
            .onCompletion()
            .onCompleteOnly()
            .onWhen(header("CamelKafkaManualCommit"))
            .bean(KafkaDefaultConsumerManualCommit.class, "process")
            .end();
    }

}
