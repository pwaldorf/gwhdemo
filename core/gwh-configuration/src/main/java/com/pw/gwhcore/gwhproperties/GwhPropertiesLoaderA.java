package com.pw.gwhcore.gwhproperties;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.core.env.MapPropertySource;

//@Component
public class GwhPropertiesLoaderA { //implements ApplicationListener<ContextRefreshedEvent> {

    private final ConfigurableEnvironment configurableEnvironment;

    private final GwhProperties gwhProperties;

    private static final String QUERY = "select id, property_key, property_value, from properties where profile = 'dispatcher'";
    private static boolean propertiesLoaded = false;

    @Value("${gwh.jpa.db.datasource.url}")
    private String url;

    @Value("${gwh.jpa.db.datasource.className}")
    private String driverClassName;

    @Value("${gwh.jpa.db.datasource.username}")
    String username;

    @Value("${gwh.jpa.db.datasource.password}")
    String password;

    public GwhPropertiesLoaderA(ConfigurableEnvironment configurableEnvironment, GwhProperties gwhProperties) {
        this.configurableEnvironment = configurableEnvironment;
        this.gwhProperties = gwhProperties;
    }

    //@Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (propertiesLoaded) {
            return;
        }

        PropertySource<?> appConfigProp = configurableEnvironment.getPropertySources().get("applicationConfig: [classpath:/application.yaml]");
        // Map<String, Object> propertySource = getPropertyMapFromDatabase(appConfigProp);
        Map<String, Object> propertySource = new HashMap<>();
        gwhProperties.getProperties().forEach((key, value) -> propertySource.put((String) key, value));
        configurableEnvironment.getPropertySources().addFirst(new MapPropertySource("database", propertySource));
        propertiesLoaded = true;
    }

    private Map<String, Object> getPropertyMapFromDatabase(PropertySource<?> appConfigProp) {

        Map<String, Object> propertySource = new HashMap<>();

        // Now check for database properties
        DataSource ds = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            if (!(driverClassName == null)) {
                Driver driver = (Driver) Class.forName(driverClassName).getDeclaredConstructor().newInstance();
                DriverManager.registerDriver(driver);
            }
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement(QUERY);
            rs = preparedStatement.executeQuery();
            // Populate all properties into the property source
            while (rs.next()) {
                final String propName = rs.getString("property_key");
                final String propValue = rs.getString("property_value");
                propertySource.put(propName, propValue);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                // supress
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                // supress
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                // supress
            }
        }
        return propertySource;
    }

}
