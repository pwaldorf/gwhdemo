package com.pw.ftpcomponent.routetemplates;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.spi.RoutePolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.support.route.AbstractReaderTemplate;

import java.util.List;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.reader.enabled", havingValue = "true", matchIfMissing = false)
public class FtpReaderTemplate extends AbstractReaderTemplate {

    private List<RoutePolicy> routePolicies;

    public FtpReaderTemplate(@Qualifier("ftpEndpointConsumer") EndpointConsumerBuilder endpointConsumerBuilder,
                             @Qualifier("ftpRoutePolicies") List<RoutePolicy> routePolicies) {
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
            .templateParameter("autoStart", "false")
            .templateParameter("delay", "10000")
            .templateParameter("streamDownload", "true")
            .templateParameter("stepwise", "false")
            .templateParameter("bridgeErrorHandler", "true")
            .templateParameter("completedFolder", "completed")
            .templateParameter("errorFolder", "error")
            .from(endpointConsumerBuilder)
                .routePolicy(routePolicies(routePolicies)).autoStartup("{{autoStart}}")
                .routeConfigurationId(".*ftpError.*,.*ftpConfig.*")
                .to("direct:{{directName}}");

    }

}