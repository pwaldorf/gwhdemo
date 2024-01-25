package com.pw.pgpcomponents.routeconfigurations;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteConfigurationBuilder;
import org.apache.camel.converter.crypto.PGPDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.pgpencryption.enabled", havingValue = "true", matchIfMissing = false)
public class EncryptionRouteConfigurations extends RouteConfigurationBuilder {

    @Autowired
    PGPDataFormat pgpPublicDataFormat;
    
    @Autowired
    PGPDataFormat pgpPrivateDataFormat;

    @Override
    public void configuration() throws Exception {
        
        routeConfiguration()        
        .interceptFrom("^(jms|kafka).*")
        .marshal(pgpPublicDataFormat)
        .setHeader("GWHBodyEncrypted").constant("true", String.class)
        .log(LoggingLevel.DEBUG, "Encrypted Message: ${body}");

        routeConfiguration()
        .interceptSendToEndpoint("^(jms|kafka).*")
        .choice()
            .when(header("GWHBodyEncrypted").isEqualTo("true"))
                .unmarshal(pgpPrivateDataFormat)
                .removeHeaders("GWHBodyEncrypted")
            .end();
    }
    
}
