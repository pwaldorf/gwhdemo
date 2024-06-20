package com.pw.ftpdefault1.consumer.routetemplates;

import com.pw.ftpdefault1.consumer.configurations.FtpConsumerProperties;
import com.pw.ftpdefault1.consumer.configurations.FtpConsumerRoutePolicyBuilder;
import com.pw.support1.model.GwhRoutePolicies;
import com.pw.support1.route.GwhAbstractRouteTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.default1.consumer.enabled", havingValue = "true")
public class FtpConsumerTemplate extends GwhAbstractRouteTemplate {

    private GwhRoutePolicies routePolicies = new GwhRoutePolicies();
    private final FtpConsumerProperties ftpConsumerProperties;

    public FtpConsumerTemplate(FtpConsumerProperties ftpConsumerProperties) {
        this.ftpConsumerProperties = ftpConsumerProperties;
        routePolicies = getRoutePolicies(FtpConsumerRoutePolicyBuilder.class);
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
            .from(getConsumerEndpointRouteBuilderByName(ftpConsumerProperties.getDefaultConsumerEndpoint()).getConsumerEndpoint())
                .routePolicy(routePolicies.toArrayRoutePolicies())
                .autoStartup("{{autoStart}}")
                .routeConfigurationId(".*ftpError.*,.*ftpConfig.*")
                .to("direct:{{directName}}");

    }
}