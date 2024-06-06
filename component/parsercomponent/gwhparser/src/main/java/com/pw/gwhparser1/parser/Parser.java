package com.pw.gwhparser1.parser;

import java.io.BufferedReader;

import com.pw.gwhparser1.parser.model.Entity;

public interface Parser {

    public Entity parse(String text) throws RuntimeException;

    public Entity parse(BufferedReader reader) throws RuntimeException;
}
