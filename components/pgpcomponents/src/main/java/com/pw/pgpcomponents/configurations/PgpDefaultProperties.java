package com.pw.pgpcomponents.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gwh.component.pgp.default")
public class PgpDefaultProperties {

    private String publicKeyUserId;
    private String publicKeyPassword;
    private String publicKeyFileName;

    private String privateKeyUserId;
    private String privateKeyPassword;
    private String privateKeyFileName;

    private String receiverPattern = "^(jms|kafka|activemq).*";
    private String senderPattern = "^(jms|kafka|activemq).*";

}
