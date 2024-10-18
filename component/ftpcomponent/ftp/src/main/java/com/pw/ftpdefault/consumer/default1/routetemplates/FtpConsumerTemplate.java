package com.pw.ftpdefault.consumer.default1.routetemplates;

import com.pw.ftpdefault.consumer.default1.configurations.FtpConsumerProperties;
import com.pw.support1.route.GwhEndpointRouteBuilderExtension;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftpdefault.consumer.default1.enabled", havingValue = "true")
public class FtpConsumerTemplate extends EndpointRouteBuilder implements GwhEndpointRouteBuilderExtension {

    private final FtpConsumerProperties ftpConsumerProperties;

    public FtpConsumerTemplate(FtpConsumerProperties ftpConsumerProperties) {
        this.ftpConsumerProperties = ftpConsumerProperties;
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("ftp_reader_v1")
            .templateParameter("fileComponent", "ftp")
            .templateParameter("directName")
            .templateParameter("ftpusername")
            .templateParameter("ftppassword")
            .templateParameter("ftpserver")
            .templateParameter("ftpport", "21")
            .templateParameter("directory", "pub")
            .templateParameter("fileName")
            .templateParameter("autoStart", "false")
            .templateParameter("delay", "10000")
            .templateParameter("streamDownload", "true")
            .templateParameter("stepwise", "false")
            .templateParameter("bridgeErrorHandler", "true")
            .templateParameter("completedFolder", "completed")
            .templateParameter("errorFolder", "error")
            .from(getConsumerEndpointRouteBuilderByName(ftpConsumerProperties.getDefaultConsumerEndpoint()).getConsumerEndpoint())
                // .routePolicy(getRoutePolicies(FtpConsumerRoutePolicyBuilder.class))
                .routePolicy(getRoutePoliciesByAnnotation(ftpConsumerProperties.getDefaultRoutePolicy()))
                .autoStartup("{{autoStart}}")
                .routeConfigurationId(ftpConsumerProperties.getDefaultRouteConfigurationPattern())
                .to("direct:{{directName}}");

    }
}