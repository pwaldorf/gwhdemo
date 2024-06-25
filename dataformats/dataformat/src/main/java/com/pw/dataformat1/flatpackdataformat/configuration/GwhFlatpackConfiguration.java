package com.pw.dataformat1.flatpackdataformat.configuration;


public class GwhFlatpackConfiguration {

    private boolean fixedFile;
    private char delimiter;
    private char textQualifier;
    private boolean ignoreFirstRecord;
    private boolean allowShortLines;
    private boolean ignoreExtraColumns;

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
    public boolean isAllowShortLines() {
        return allowShortLines;
    }
    public void setAllowShortLines(boolean allowShortLines) {
        this.allowShortLines = allowShortLines;
    }
    public boolean isIgnoreExtraColumns() {
        return ignoreExtraColumns;
    }
    public void setIgnoreExtraColumns(boolean ignoreExtraColumns) {
        this.ignoreExtraColumns = ignoreExtraColumns;
    }
    public String getFormatDefinition() {
        return formatDefinition;
    }
    public void setFormatDefinition(String formatDefinition) {
        this.formatDefinition = formatDefinition;
    }

}
