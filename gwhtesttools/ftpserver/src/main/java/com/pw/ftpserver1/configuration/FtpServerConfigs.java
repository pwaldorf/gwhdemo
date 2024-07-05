package com.pw.ftpserver1.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.config.spring.factorybeans.ConnectionConfigFactoryBean;
import org.apache.ftpserver.config.spring.factorybeans.DataConnectionConfigurationFactoryBean;
import org.apache.ftpserver.filesystem.nativefs.NativeFileSystemFactory;
import org.apache.ftpserver.ftplet.FileSystemFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import com.pw.ftpserver1.bean.FtpServerBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile("local")
@ConditionalOnProperty(value = "gwh.framework.devtool.ftp.server.enabled", havingValue = "true")
public class FtpServerConfigs {

    FtpServerProperties properties;

    public FtpServerConfigs(FtpServerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public FtpServerBean ftpServerInstance(Listener listener, UserManager userManager,
                                            FileSystemFactory fileSystemFactory, ConnectionConfigFactory connectionConfigFactory) {
        log.info("Starting FTP Server");
        FtpServerFactory serverFactory = new FtpServerFactory();
        serverFactory.setConnectionConfig(connectionConfigFactory.createConnectionConfig());
        //serverFactory.setFtplets(Collections.singletonMap("custom", new CustomFtplet()));
        serverFactory.setListeners(Collections.singletonMap("default", listener));
        serverFactory.setUserManager(userManager);
        serverFactory.setFileSystem(fileSystemFactory);
        return new FtpServerBean(serverFactory.createServer());
    }

    @Bean
    public ConnectionConfigFactoryBean connectionConfigFactory(FtpServerProperties properties) {
        ConnectionConfigFactoryBean connectionConfigFactoryBean = new ConnectionConfigFactoryBean();
        connectionConfigFactoryBean.setAnonymousLoginEnabled(true);
        connectionConfigFactoryBean.setMaxLogins(properties.getMaxLogins());
        return connectionConfigFactoryBean;
    }

    @Bean
    public Listener listener(FtpServerProperties properties,
                             DataConnectionConfigurationFactory connectionConfigurationFactory) throws IOException {
        ListenerFactory factory = new ListenerFactory();
        factory.setDataConnectionConfiguration(connectionConfigurationFactory.createDataConnectionConfiguration());
        factory.setServerAddress(properties.getHost());
        factory.setPort(properties.getPort());
        return factory.createListener();
    }

    @Bean
    public DataConnectionConfigurationFactoryBean dataConnectionConfigurationFactory() {
        DataConnectionConfigurationFactoryBean connectionConfigurationFactoryBean = new DataConnectionConfigurationFactoryBean();
        connectionConfigurationFactoryBean.setPassivePorts("2122-2199");
        return connectionConfigurationFactoryBean;
    }

    @Bean
    public UserManager userManager(FtpServerProperties properties) throws FtpException, FileNotFoundException, IOException {
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();

        //String tempPath = System.getProperty("java.io.tmpdir") + System.currentTimeMillis() + ".properties";
        //File tempConfig = new File(tempPath);
        ClassPathResource resource = new ClassPathResource("users.properties");
        //IOUtils.copy(resource.getInputStream(), new FileOutputStream(tempConfig));
        //userManagerFactory.setFile(tempConfig);
        userManagerFactory.setFile(resource.getFile());
        userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
        userManagerFactory.setAdminName("admin");
        UserManager userManager = userManagerFactory.createUserManager();
//        BaseUser user = new BaseUser();
//        user.setName("pwaldorf");
//        user.setPassword("password");
//        user.setHomeDirectory("/tmp");
//        userManager.save(user);
        return userManager;
    }

    @Bean
    public FileSystemFactory fileSystemFactory() {
        FileSystemFactory fileSystemFactory = new NativeFileSystemFactory();
        return fileSystemFactory;
    }



}
