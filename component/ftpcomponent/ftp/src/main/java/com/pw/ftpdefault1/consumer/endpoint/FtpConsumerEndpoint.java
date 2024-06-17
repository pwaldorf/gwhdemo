package com.pw.ftpdefault1.consumer.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.ftp;

import com.pw.ftpdefault1.consumer.configurations.FtpConsumerEndpointBuilder;
import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.FtpEndpointBuilderFactory.AdvancedFtpEndpointConsumerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "gwh.component.ftp.default1.consumer", name = "protocol", havingValue = "ftp", matchIfMissing = false)
public class FtpConsumerEndpoint implements FtpConsumerEndpointBuilder {

    @Override
    public EndpointConsumerBuilder getConsumerEndpoint() {

        AdvancedFtpEndpointConsumerBuilder ftpEndpointConsumerBuilder =

            ftp("ftp", "{{server}}:{{port}}/{{directory}}")
                    .username("{{username}}")
                    .password("{{password}}")
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

        return ftpEndpointConsumerBuilder;

    }
}