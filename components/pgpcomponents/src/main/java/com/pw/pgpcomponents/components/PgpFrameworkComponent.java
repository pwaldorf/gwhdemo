package com.pw.pgpcomponents.components;

import org.apache.camel.converter.crypto.PGPDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PgpFrameworkComponent {
    
    @Bean("pgpPublicDataFormat")    
    public PGPDataFormat pgpPublicDataFormat() {
        PGPDataFormat pgpDataFormat = new PGPDataFormat();
        pgpDataFormat.setKeyUserid("pw company <philip.waldorf@gmail.com>");        
        pgpDataFormat.setPassword("xxxxxxx");
        pgpDataFormat.setKeyFileName("file:/home/pwaldorf/.gnupg/pubring.gpg");        
        return pgpDataFormat;
    }

    @Bean("pgpPrivateDataFormat")    
    public PGPDataFormat pgpPrivateDataFormat() {
        PGPDataFormat pgpDataFormat = new PGPDataFormat();
        pgpDataFormat.setKeyUserid("pw company <philip.waldorf@gmail.com>");        
        pgpDataFormat.setPassword("xxxxxxx");
        pgpDataFormat.setKeyFileName("file:/home/pwaldorf/.gnupg/secring.gpg");        
        return pgpDataFormat;
    }

}
