package com.pw.gwhparser1.parser.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleEntityFactory implements EntityFactory {

    private static final SimpleEntityFactory instance;

    static {
        try {
            instance = new SimpleEntityFactory();
        } catch (Exception e) {
            String errMsg = "Error while initializing SimpleEntityFactory";
            log.error(errMsg, e);
            throw new ExceptionInInitializerError(errMsg);
        }
    }

    private SimpleEntityFactory() {
        super();
        if (instance != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    public static SimpleEntityFactory getInstance() {
        return instance;
    }

    @Override
    public Entity newRootEntity(String name, String source) {
        return new SimpleEntity(name, null);
    }

    @Override
    public Entity newSubEntity(String name, Entity parent, String source) {
        return new SimpleEntity(name, parent);
    }

}
