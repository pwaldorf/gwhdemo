package com.pw.gwhcore.configurations;

import javax.sql.DataSource;

import org.apache.commons.configuration2.DatabaseConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.spring.ConfigurationPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@PropertySource(value = {"classpath:application.yaml"}, ignoreResourceNotFound = true)
public class SpringPropertiesConfig {

    private GwhCoreProperties gwhCoreProperties;

    private org.springframework.core.env.Environment env;

    DataSource dataSource;

    public SpringPropertiesConfig(GwhCoreProperties gwhCoreProperties, org.springframework.core.env.Environment env, DataSource dataSource) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.env = env;
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void initializeDatabasePropertySourceUsage() {
        MutablePropertySources propertySources = ((ConfigurableEnvironment) env).getPropertySources();
        Parameters params = new Parameters();

        try {
            log.info("Initializing DatabasePropertySource");
            BasicConfigurationBuilder<DatabaseConfiguration> builder =
                        new BasicConfigurationBuilder<DatabaseConfiguration>(DatabaseConfiguration.class);
            builder.configure(
                params.database()
                    .setDataSource(dataSource)
                    .setTable(gwhCoreProperties.getConfigTableName())
                    .setKeyColumn(gwhCoreProperties.getConfigKeyColumnName())
                    .setValueColumn(gwhCoreProperties.getConfigValueColumnName())
                    .setConfigurationNameColumn(gwhCoreProperties.getConfigNameColumnName())
                    .setConfigurationName(gwhCoreProperties.getProfile())
                    // .setTable("gwh_configs")
                    // .setKeyColumn("config_key")
                    // .setValueColumn("config_value")
                    // .setConfigurationNameColumn("config_name")
                    // .setConfigurationName("dispatch")
                    .setThrowExceptionOnMissing(true)
            );

            ConfigurationPropertySource configurationPropertySource = new ConfigurationPropertySource("Database",
                                                                                                        builder.getConfiguration());
            propertySources.addFirst(configurationPropertySource);
        } catch (Exception e) {
            log.error("Error initializing DatabasePropertySource", e);
        } finally {
            log.info("DatabasePropertySource initialized");
        }
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    // @Bean
    // public DataSource dataSource() {
    //     BasicDataSource dataSource = new BasicDataSource();
    //     dataSource.setDriverClassName("org.h2.Driver");
    //     dataSource.setUrl("jdbc:h2:mem:gwh");
    //     dataSource.setUsername("pwaldorf");
    //     dataSource.setPassword("pwaldorf");
    //     return dataSource;
    // }

}
