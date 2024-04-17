package com.pw.gwhparser.parser.enums;

public enum CsvDelimiter {
    COMMA(','),
    PIPE('|'),
    SEMICOLON(';');

    private char delimiter;

    public char getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    CsvDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

}
