package com.pw.ftpcomponent.consumer.routetemplates;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.pw.ftpcomponent.consumer.configurations.FtpConsumerEndpointBuilder;
import com.pw.ftpcomponent.consumer.configurations.FtpConsumerRoutePolicies;
import com.pw.support.route.AbstractConsumerTemplate;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.consumer.enabled", havingValue = "true")
public class FtpConsumerTemplate extends AbstractConsumerTemplate<FtpConsumerEndpointBuilder> {

    public FtpConsumerTemplate(FtpConsumerEndpointBuilder endpointConsumerBuilder, @Nullable FtpConsumerRoutePolicies routePolicies) {
        super(endpointConsumerBuilder, routePolicies);
    }

    @Override
    public void configure() throws Exception {

        routeTemplate("ftp_reader_v1")
            .templateParameter("fileComponent", "ftp")
            .templateParameter("directName")
            .templateParameter("username")
            .templateParameter("password")
            .templateParameter("server")
            .templateParameter("port", "21")
            .templateParameter("directory", "pub")
            .templateParameter("fileName")
            .templateParameter("autoStart", "false")
            .templateParameter("delay", "10000")
            .templateParameter("streamDownload", "true")
            .templateParameter("stepwise", "false")
            .templateParameter("bridgeErrorHandler", "true")
            .templateParameter("completedFolder", "completed")
            .templateParameter("errorFolder", "error")
            .from(super.getEndpointConsumerBuilder().getConsumerEndpoint())
                .routePolicy(super.getRoutePolicies().getRoutePolicies().toArrayRoutePolicies())
                .autoStartup("{{autoStart}}")
                .routeConfigurationId(".*ftpError.*,.*ftpConfig.*")
                .to("direct:{{directName}}");

    }

}