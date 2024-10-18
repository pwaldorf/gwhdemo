package com.pw.ftpdefault.consumer.default1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.ftp;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.FtpEndpointBuilderFactory.AdvancedFtpEndpointConsumerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.ftpdefault.consumer.default1.configurations.FtpConsumerEndpointBuilder;

@Slf4j
@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftpdefault.consumer.default1.enabled", havingValue = "true")
public class FtpConsumerEndpoint implements FtpConsumerEndpointBuilder {

    @Override
    public EndpointConsumerBuilder getConsumerEndpoint() {

        AdvancedFtpEndpointConsumerBuilder ftpEndpointConsumerBuilder =

            ftp("ftp", "{{ftpserver}}:{{ftpport}}/{{directory}}")
                    .username("{{ftpusername}}")
                    .password("{{ftppassword}}")
                    .fileName("{{fileName}}")
                    .streamDownload("{{streamDownload}}")
                    .move("{{completedFolder}}/${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}")
                    .moveFailed("{{errorFolder}}/${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}")
                    .delay("{{delay}}")
                    .backoffErrorThreshold(1)
                    .backoffMultiplier(10)
                    .advanced()
                        .stepwise("{{stepwise}}")
                        .bridgeErrorHandler("{{bridgeErrorHandler}}");

        log.info("ftpEndpointConsumerBuilder: {}", ftpEndpointConsumerBuilder);

        return ftpEndpointConsumerBuilder;

    }
}
