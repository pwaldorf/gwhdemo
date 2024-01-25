package com.pw.pgpcomponents.components;

import org.apache.camel.converter.crypto.PGPDataFormat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.component.pgpencryption.enabled", havingValue = "true", matchIfMissing = false)
public class PgpFrameworkComponent {
    
    @Bean("pgpPublicDataFormat")    
    public PGPDataFormat pgpPublicDataFormat() {
        PGPDataFormat pgpDataFormat = new PGPDataFormat();
        pgpDataFormat.setKeyUserid("pw company <philip.waldorf@gmail.com>");        
        pgpDataFormat.setPassword("Adriana77!");
        pgpDataFormat.setKeyFileName("file:/home/pwaldorf/.gnupg/pubring.gpg");        
        return pgpDataFormat;
    }

    @Bean("pgpPrivateDataFormat")    
    public PGPDataFormat pgpPrivateDataFormat() {
        PGPDataFormat pgpDataFormat = new PGPDataFormat();
        pgpDataFormat.setKeyUserid("pw company <philip.waldorf@gmail.com>");        
        pgpDataFormat.setPassword("Adriana77!");
        pgpDataFormat.setKeyFileName("file:/home/pwaldorf/.gnupg/secring.gpg");        
        return pgpDataFormat;
    }

}
