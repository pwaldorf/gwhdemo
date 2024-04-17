package com.pw.gwhparser.parser;

import java.io.BufferedReader;

import com.pw.gwhparser.parser.model.Entity;

public interface Parser {

    public Entity parse(String text) throws RuntimeException;

    public Entity parse(BufferedReader reader) throws RuntimeException;
}
