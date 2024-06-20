package com.pw.gwhcore1.gwhproperties2;

import com.pw.api1.configuration.GwhPropertiesResource;
import com.pw.api1.configuration.GwhProperty;
import com.pw.gwhcore1.GwhConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GwhDatabasePropertiesResource implements GwhPropertiesResource {

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final GwhDatabaseProperties gwhDatabaseProperties;

    public GwhDatabasePropertiesResource(GwhConfigurationProperties gwhConfigurationProperties, GwhDatabaseProperties gwhDatabaseProperties) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.gwhDatabaseProperties = gwhDatabaseProperties;
    }

    @Override
    public List<GwhProperty> getResourceAll() {
        getPropertiesFromDatabase(gwhConfigurationProperties, gwhDatabaseProperties, getPropertiesQuery(false, false));

        return List.of();
    }

    @Override
    public List<GwhProperty> getResourceByProfile(String profile) {
        getPropertiesFromDatabase(gwhConfigurationProperties, gwhDatabaseProperties, getPropertiesQuery(true, false));
        return List.of();
    }

    @Override
    public List<GwhProperty> getResourceByProfileAndRegion(String profile, String region) {
        getPropertiesFromDatabase(gwhConfigurationProperties, gwhDatabaseProperties, getPropertiesQuery(true, true));
        return List.of();
    }

    private String getPropertiesQuery(boolean isByProfile, boolean isByRegion) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(gwhDatabaseProperties.getKeyColumn());
        query.append(",");
        query.append(gwhDatabaseProperties.getValueColumn());
        query.append(" FROM ");
        query.append(gwhDatabaseProperties.getTable());
        if (isByProfile) {
            query.append(" WHERE ");
            query.append(gwhDatabaseProperties.getProfileColumn());
            query.append(" = ? ");

            if (isByRegion) {
                query.append(" AND ");
                query.append(gwhDatabaseProperties.getRegionColumn());
                query.append(" = ? ");
            }
        }
        log.info("Properties Query: {}", query.toString());
        return query.toString();
    }

    private Map<String, Object> getPropertiesFromDatabase(GwhConfigurationProperties gwhConfigurationProperties,
                                                          GwhDatabaseProperties gwhDatabaseProperties, String query) {
        Map<String, Object> propertySource = new HashMap<>();
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
                propertySource.put(property_name, property_value);
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
