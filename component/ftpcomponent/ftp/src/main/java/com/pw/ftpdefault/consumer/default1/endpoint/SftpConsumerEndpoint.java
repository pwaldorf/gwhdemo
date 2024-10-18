package com.pw.ftpdefault.consumer.default1.endpoint;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.sftp;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.SftpEndpointBuilderFactory.AdvancedSftpEndpointConsumerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.ftpdefault.consumer.default1.configurations.FtpConsumerEndpointBuilder;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftpdefault.consumer.default1.enabled", havingValue = "true")
public class SftpConsumerEndpoint implements FtpConsumerEndpointBuilder {

    @Override
    public EndpointConsumerBuilder getConsumerEndpoint() {

        AdvancedSftpEndpointConsumerBuilder sftpEndpointConsumerBuilder =

            sftp("sftp", "{{ftpserver}}:{{ftpport}}/{{directory}}")
                    .username("user")
                    .fileName("resourceAttr.getFileName()")
                    .privateKeyFile("{{privateKeyFile}}")
                    .streamDownload(true)
                    .move("completed/${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}")
                    .moveFailed("error/${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}")
                    .delay(10000)
                    .initialDelay(5000)
                    .useFixedDelay(true)
                    .backoffErrorThreshold(1)
                    .backoffMultiplier(10)
                    .advanced()
                        .stepwise("{{stepwise}}")
                        .bridgeErrorHandler(true);

        return sftpEndpointConsumerBuilder;

    }

}
