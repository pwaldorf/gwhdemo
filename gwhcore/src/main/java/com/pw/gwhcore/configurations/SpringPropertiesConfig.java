package com.pw.gwhcore.configurations;

import javax.sql.DataSource;

import org.apache.commons.configuration2.DatabaseConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.spring.ConfigurationPropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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

    private DataSource dataSource;

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
            // DatabaseConfiguration can be changed to a MapConfiguration to make the selection of properties more robust
            // i.e. Select by environment and service name
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

    // @Bean
    // public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    //     return new PropertySourcesPlaceholderConfigurer();
    // }
}
