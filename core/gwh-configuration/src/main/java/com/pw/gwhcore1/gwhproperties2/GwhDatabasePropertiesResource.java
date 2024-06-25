package com.pw.gwhcore1.gwhproperties2;

import com.pw.api1.configuration.GwhPropertiesResource;
import com.pw.api1.configuration.GwhProperty;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.gwhproperties.GwhDefaultProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GwhDatabasePropertiesResource implements GwhPropertiesResource {

    private final GwhDatabaseProperties gwhDatabaseProperties;
    private final GwhConfigurationProperties gwhConfigurationProperties;

    public GwhDatabasePropertiesResource(ApplicationContextInitializedEvent event) {

        gwhDatabaseProperties = Binder.get(
                event.getApplicationContext().getEnvironment()).bind("gwh.properties.db",
                Bindable.of(GwhDatabaseProperties.class)).get();

        gwhConfigurationProperties = Binder.get(
                event.getApplicationContext().getEnvironment()).bind("gwh.service",
                Bindable.of(GwhConfigurationProperties.class)).get();
    }

    @Override
    public List<GwhProperty> getResourceAll() {
        return getPropertiesFromDatabase(gwhConfigurationProperties,
                gwhDatabaseProperties,
                getPropertiesQuery(false, false));
    }

    @Override
    public List<GwhProperty> getResourceByProfile(String profile) {
        return getPropertiesFromDatabase(gwhConfigurationProperties,
                gwhDatabaseProperties,
                getPropertiesQuery(true, false));
    }

    @Override
    public List<GwhProperty> getResourceByProfileAndRegion(String profile, String region) {
        return getPropertiesFromDatabase(gwhConfigurationProperties,
                gwhDatabaseProperties,
                getPropertiesQuery(true, true));
    }

    private String getPropertiesQuery(boolean isByProfile, boolean isByRegion) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT property_key, property_value FROM profile_properties prof ");
        query.append("JOIN properties prop ON prop.region = prof.region AND prop.property = prof.property");
        if (isByProfile) {
            query.append(" WHERE prof.profile = ?");

            if (isByRegion) {
                query.append(" AND prof.region = ?");
            }
        }
        log.info("Properties Query: {}", query.toString());
        return query.toString();
    }

    private List<GwhProperty> getPropertiesFromDatabase(GwhConfigurationProperties gwhConfigurationProperties,
                                                          GwhDatabaseProperties gwhDatabaseProperties, String query) {
        List<GwhProperty> propertySource = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(gwhDatabaseProperties.getUrl(),
                    gwhDatabaseProperties.getUserName(),
                    gwhDatabaseProperties.getPassword());
            preparedStatement = connection.prepareStatement(query);
            if (StringUtils.isNotEmpty(gwhConfigurationProperties.getProfile()) &&
                    !("ALL").equalsIgnoreCase(gwhConfigurationProperties.getProfile())) {
                preparedStatement.setString(1, gwhConfigurationProperties.getProfile());

                if (StringUtils.isNotEmpty(gwhConfigurationProperties.getRegion()) &&
                        !("ALL").equalsIgnoreCase(gwhConfigurationProperties.getRegion())) {
                    preparedStatement.setString(2, gwhConfigurationProperties.getRegion());
                }
            }

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                final String property_name = resultSet.getString(gwhDatabaseProperties.getKeyColumn());
                final String property_value = resultSet.getString(gwhDatabaseProperties.getValueColumn());
                GwhProperty gwhProperty = new GwhDefaultProperty(property_name, property_value);
                propertySource.add(gwhProperty);
                log.debug("Loading property key: {} value: {}", property_name, property_value);
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                log.info(e.getMessage());
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                log.info(e.getMessage());
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.info(e.getMessage());
            }
        }
        return propertySource;
    }
}
