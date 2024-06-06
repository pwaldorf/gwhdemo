package com.pw.ftpserver1.bean;

import org.apache.ftpserver.FtpServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class FtpServerBean implements InitializingBean, DisposableBean {

    private final FtpServer ftpServer;

    public FtpServerBean(FtpServer ftpServer) {
        this.ftpServer = ftpServer;
    }

    @Override
    public void destroy() throws Exception {
        this.ftpServer.stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.ftpServer.start();
    }

}
