package com.pw.gwhparser.parser.model;

public interface EntityFactory {

    public Entity newRootEntity(String name, String source);

    public Entity newSubEntity(String name, Entity parent, String source);
}
