package com.pw.ftpdefault1.consumer.routetemplates;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.ftpdefault1.consumer.configurations.FtpConsumerEndpointBuilder;
import com.pw.ftpdefault1.consumer.configurations.FtpConsumerRoutePolicies;
import com.pw.support1.route.GwhAbstractRouteTemplate;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.default1.consumer.enabled", havingValue = "true")
public class FtpConsumerTemplate extends GwhAbstractRouteTemplate<FtpConsumerEndpointBuilder> {

    private final FtpConsumerRoutePolicies routePolicies;

    public FtpConsumerTemplate(FtpConsumerEndpointBuilder endpointConsumerBuilder, FtpConsumerRoutePolicies routePolicies) {
        super(endpointConsumerBuilder);
        this.routePolicies = routePolicies;
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
            .templateParameter("autoStart", "true")
            .templateParameter("delay", "10000")
            .templateParameter("streamDownload", "true")
            .templateParameter("stepwise", "false")
            .templateParameter("bridgeErrorHandler", "true")
            .templateParameter("completedFolder", "completed")
            .templateParameter("errorFolder", "error")
            .from(super.getEndpointRouteBuilder().getConsumerEndpoint())
                .routePolicy(routePolicies.getRoutePolicies().toArrayRoutePolicies())
                .autoStartup("{{autoStart}}")
                .routeConfigurationId(".*ftpError.*,.*ftpConfig.*")
                .to("direct:{{directName}}");

    }
}