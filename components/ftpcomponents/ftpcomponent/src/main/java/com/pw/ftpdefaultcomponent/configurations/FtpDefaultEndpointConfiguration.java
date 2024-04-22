package com.pw.ftpdefaultcomponent.configurations;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.ftp;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.sftp;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.FtpEndpointBuilderFactory.AdvancedFtpEndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.dsl.SftpEndpointBuilderFactory.AdvancedSftpEndpointConsumerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.ftp.reader.enabled", havingValue = "true", matchIfMissing = false)
public class FtpDefaultEndpointConfiguration {

    @Bean("ftpEndpointConsumer")
    @Profile("local")
    public EndpointConsumerBuilder ftpEndpointConsumerBuilder() {

        AdvancedFtpEndpointConsumerBuilder ftpEndpointConsumerBuilder =

            ftp("ftp", "{{server}}:{{port}}/{{directory}}")
                    .username("{{user}}")
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

    @Bean("ftpEndpointConsumer")
    @Profile("!local")
    public EndpointConsumerBuilder sftpEndpointConsumerBuilder() {

        AdvancedSftpEndpointConsumerBuilder sftpEndpointConsumerBuilder =

            sftp("sftp", "{{server}}:{{port}}/{{directory}}")
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
