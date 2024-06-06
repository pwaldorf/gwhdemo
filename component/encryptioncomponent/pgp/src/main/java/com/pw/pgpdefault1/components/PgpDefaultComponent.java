package com.pw.pgpdefault1.components;

import org.apache.camel.converter.crypto.PGPDataFormat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pw.pgpdefault1.configurations.PgpDefaultProperties;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.pgpencryption.default1.enabled", havingValue = "true", matchIfMissing = false)
public class PgpDefaultComponent {

    PgpDefaultProperties pgpDefaultProperties;

    public PgpDefaultComponent(PgpDefaultProperties pgpDefaultProperties) {
        this.pgpDefaultProperties = pgpDefaultProperties;
    }

    @Bean("pgpPublicDataFormat")
    public PGPDataFormat pgpPublicDataFormat() {
        PGPDataFormat pgpDataFormat = new PGPDataFormat();
        pgpDataFormat.setKeyUserid(pgpDefaultProperties.getPublicKeyUserId());
        pgpDataFormat.setPassword(pgpDefaultProperties.getPublicKeyPassword());
        pgpDataFormat.setKeyFileName(pgpDefaultProperties.getPublicKeyFileName());
        return pgpDataFormat;
    }

    @Bean("pgpPrivateDataFormat")
    public PGPDataFormat pgpPrivateDataFormat() {
        PGPDataFormat pgpDataFormat = new PGPDataFormat();
        pgpDataFormat.setKeyUserid(pgpDefaultProperties.getPrivateKeyUserId());
        pgpDataFormat.setPassword(pgpDefaultProperties.getPrivateKeyPassword());
        pgpDataFormat.setKeyFileName(pgpDefaultProperties.getPrivateKeyFileName());
        return pgpDataFormat;
    }

}
