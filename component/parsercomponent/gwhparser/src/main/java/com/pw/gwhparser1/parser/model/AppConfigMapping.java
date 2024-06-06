package com.pw.gwhparser1.parser.model;

public class AppConfigMapping {

    private String appConfigMappingKey;
    private String appKey;
    private String service;
    private String additionalCriteria;
    private String appConfigKey;
    private String properties;


    public AppConfigMapping() {
    }

    public AppConfigMapping(String appConfigMappingKey, String appKey, String service, String additionalCriteria,
            String appConfigKey, String properties) {
        this.appConfigMappingKey = appConfigMappingKey;
        this.appKey = appKey;
        this.service = service;
        this.additionalCriteria = additionalCriteria;
        this.appConfigKey = appConfigKey;
        this.properties = properties;
    }

    public String getAppConfigMappingKey() {
        return appConfigMappingKey;
    }
    public void setAppConfigMappingKey(String appConfigMappingKey) {
        this.appConfigMappingKey = appConfigMappingKey;
    }
    public String getAppKey() {
        return appKey;
    }
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
    public String getAdditionalCriteria() {
        return additionalCriteria;
    }
    public void setAdditionalCriteria(String additionalCriteria) {
        this.additionalCriteria = additionalCriteria;
    }
    public String getAppConfigKey() {
        return appConfigKey;
    }
    public void setAppConfigKey(String appConfigKey) {
        this.appConfigKey = appConfigKey;
    }
    public String getProperties() {
        return properties;
    }
    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "AppConfigMapping [appConfigMappingKey=" + appConfigMappingKey + ", appKey=" + appKey + ", service="
                + service + ", additionalCriteria=" + additionalCriteria + ", appConfigKey=" + appConfigKey
                + ", properties=" + properties + "]";
    }

}
