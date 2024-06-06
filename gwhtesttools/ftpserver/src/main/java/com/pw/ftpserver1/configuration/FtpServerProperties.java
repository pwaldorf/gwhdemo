package com.pw.ftpserver1.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gwh.local.ftp.server")
public class FtpServerProperties {

    private int maxLogins = 10;
    private String host = "localhost";

    // for linux, port must be greater then cat /proc/sys/net/ipv4/ip_unprivileged_port_start
    private int port = 2021;

}
