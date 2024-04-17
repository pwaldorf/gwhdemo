package com.pw.dataformats.flatpackdataformat.configuration;


public class GwhFlatpackConfiguration {

    private boolean fixedFile;
    private char delimiter;
    private char textQualifier;
    private boolean ignoreFirstRecord;
    private String formatDefinition;

    public boolean isFixedFile() {
        return fixedFile;
    }
    public void setFixedFile(boolean fixedFile) {
        this.fixedFile = fixedFile;
    }
    public char getDelimiter() {
        return delimiter;
    }
    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }
    public char getTextQualifier() {
        return textQualifier;
    }
    public void setTextQualifier(char textQualifier) {
        this.textQualifier = textQualifier;
    }
    public boolean isIgnoreFirstRecord() {
        return ignoreFirstRecord;
    }
    public void setIgnoreFirstRecord(boolean ignoreFirstRecord) {
        this.ignoreFirstRecord = ignoreFirstRecord;
    }
    public String getFormatDefinition() {
        return formatDefinition;
    }
    public void setFormatDefinition(String formatDefinition) {
        this.formatDefinition = formatDefinition;
    }

}
