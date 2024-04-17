package com.pw.dataformats.gatewaydataformat.configuration;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.pw.gwhparser.parser.constants.ParserConstants;
import com.pw.gwhparser.parser.enums.CsvDelimiter;
import com.pw.gwhparser.parser.enums.ParserFormat;
import com.pw.gwhparser.parser.enums.ValueType;

public class GwhGatewayConfiguration {

    // Shared Configs
    private ParserFormat format = ParserFormat.XML;
    private String characterSet = "UTF-8";
    private List<DateTimeFormatter> dateFormatters;
    private List<DateTimeFormatter> timestampFormatters;
    private ValueType defaultValueType = ValueType.STRING;
    private boolean preserveCDATA = false;
    private String rootEntityName = ParserConstants.ROOT;
    private String appConfig = null;

    // Formatter Only
    private boolean skipNullValues = false;
    private String templateName = null;

    // Parser Only
    private char csvDelimiter = CsvDelimiter.COMMA.getDelimiter();
    private boolean skipHeader = false;
    private boolean useMap = false;
    private String stringConcatDelimiter = " ";

    public ParserFormat getFormat() {
        return format;
    }
    public void setFormat(ParserFormat format) {
        this.format = format;
    }
    public String getCharacterSet() {
        return characterSet;
    }
    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }
    public List<DateTimeFormatter> getDateFormatters() {
        return dateFormatters;
    }
    public void setDateFormatters(List<DateTimeFormatter> dateFormatters) {
        this.dateFormatters = dateFormatters;
    }
    public ValueType getDefaultValueType() {
        return defaultValueType;
    }
    public void setDefaultValueType(ValueType defaultValueType) {
        this.defaultValueType = defaultValueType;
    }
    public boolean isPreserveCDATA() {
        return preserveCDATA;
    }
    public void setPreserveCDATA(boolean preserveCDATA) {
        this.preserveCDATA = preserveCDATA;
    }
    public String getRootEntityName() {
        return rootEntityName;
    }
    public void setRootEntityName(String rootEntityName) {
        this.rootEntityName = rootEntityName;
    }
    public String getStringConcatDelimiter() {
        return stringConcatDelimiter;
    }
    public void setStringConcatDelimiter(String stringConcatDelimiter) {
        this.stringConcatDelimiter = stringConcatDelimiter;
    }

    public List<DateTimeFormatter> getTimestampFormatters() {
        return timestampFormatters;
    }

    public void setTimestampFormatters(List<DateTimeFormatter> timestampFormatters) {
        this.timestampFormatters = timestampFormatters;
    }

    public char getCsvDelimiter() {
        return csvDelimiter;
    }

    public void setCsvDelimiter(char csvDelimiter) {
        this.csvDelimiter = csvDelimiter;
    }

    public boolean isSkipHeader() {
        return skipHeader;
    }

    public void setSkipHeader(boolean skipHeader) {
        this.skipHeader = skipHeader;
    }

    public boolean isUseMap() {
        return useMap;
    }

    public void setUseMap(boolean useMap) {
        this.useMap = useMap;
    }

    public boolean isSkipNullValues() {
        return skipNullValues;
    }

    public void setSkipNullValues(boolean skipNullValues) {
        this.skipNullValues = skipNullValues;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getAppConfig() {
        return appConfig;
    }
    public void setAppConfig(String appConfig) {
        this.appConfig = appConfig;
    }

}
