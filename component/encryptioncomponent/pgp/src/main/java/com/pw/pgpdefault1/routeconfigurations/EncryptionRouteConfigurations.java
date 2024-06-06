package com.pw.pgpdefault1.routeconfigurations;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteConfigurationBuilder;
import org.apache.camel.converter.crypto.PGPDataFormat;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.pgpdefault1.configurations.PgpDefaultProperties;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.pgpencryption.default1.enabled", havingValue = "true", matchIfMissing = false)
public class EncryptionRouteConfigurations extends RouteConfigurationBuilder {

    PgpDefaultProperties pgpDefaultProperties;

    PGPDataFormat pgpPublicDataFormat;

    PGPDataFormat pgpPrivateDataFormat;

    public EncryptionRouteConfigurations(PgpDefaultProperties pgpDefaultProperties,
                                         @Qualifier("pgpPublicDataFormat") PGPDataFormat pgpPublicDataFormat,
                                         @Qualifier("pgpPrivateDataFormat") PGPDataFormat pgpPrivateDataFormat) {
        this.pgpDefaultProperties = pgpDefaultProperties;
        this.pgpPublicDataFormat = pgpPublicDataFormat;
        this.pgpPrivateDataFormat = pgpPrivateDataFormat;
    }

    @Override
    public void configuration() throws Exception {

        routeConfiguration()
        .description("Encrypts messages from JMS and Kafka routes")
        .interceptFrom(pgpDefaultProperties.getReceiverPattern())
        .marshal(pgpPublicDataFormat)
        .marshal().base64()
        .setHeader("GWHBodyEncrypted").constant("true", String.class)
        .log(LoggingLevel.DEBUG, "Encrypted Message: ${body}");

        routeConfiguration()
        .description("Dencrypts messages to JMS and Kafka routes")
        .interceptSendToEndpoint(pgpDefaultProperties.getSenderPattern())
        .choice()
            .when(header("GWHBodyEncrypted").isEqualTo("true"))
                .unmarshal().base64()
                .unmarshal(pgpPrivateDataFormat)
                .removeHeaders("GWHBodyEncrypted")
            .end();
    }

}
