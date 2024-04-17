package com.pw.gwhparser.loader.parserconfig;

import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pw.gwhparser.parser.constants.ParserConstants;
import com.pw.gwhparser.parser.enums.CsvDelimiter;
import com.pw.gwhparser.parser.enums.ParserFormat;
import com.pw.gwhparser.parser.enums.ValueType;


public class ParserConfigProperties {

    private ParserFormat format = ParserFormat.XML;
    private String characterSet = "UTF-8";
    private boolean coalescing = false;
    private List<DateTimeFormatter> dateFormatters;
    private ValueType defaultValueType = ValueType.STRING;
    private boolean preserveCDATA = false;
    private String rootEntityName = ParserConstants.ROOT;
    private String stringConcatDelimiter = " ";
    private List<DateTimeFormatter> timestampFormatters;

    // CSV Variables
    private char csvDelimiter = CsvDelimiter.COMMA.getDelimiter();
    private boolean skipHeader = false;
    private boolean useMap = false;


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
        if (!Charset.isSupported(characterSet)) {
            throw new IllegalArgumentException("Unsupported character set " + characterSet);
        }
        this.characterSet = characterSet;
    }

    public boolean isCoalescing() {
        return coalescing;
    }

    public void setCoalescing(boolean coalescing) {
        this.coalescing = coalescing;
    }

    public List<DateTimeFormatter> getDateFormatters() {
        return (dateFormatters != null ? dateFormatters : Collections.emptyList());
    }

    public void setDateFormatters(List<DateTimeFormatter> dateFormatters) {
        this.dateFormatters = dateFormatters;
    }

    public void addDateFormatter(DateTimeFormatter formatter) {
        if (formatter != null) {
            if (dateFormatters == null) {
                dateFormatters = new ArrayList<>();
            }
            dateFormatters.add(formatter);
        }
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
        String str = (stringConcatDelimiter != null ? stringConcatDelimiter.trim() : null);
        if (stringConcatDelimiter == null || stringConcatDelimiter.isEmpty()) {
            throw new IllegalArgumentException("String concat delimiter is required");
        }
        this.stringConcatDelimiter = str;
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

    public List<DateTimeFormatter> getTimestampFormatters() {
        return (timestampFormatters != null ? timestampFormatters : Collections.emptyList());
    }

    public void setTimestampFormatters(List<DateTimeFormatter> timestampFormatters) {
        this.timestampFormatters = timestampFormatters;
    }

    public void addTimestampFormatter(DateTimeFormatter formatter) {
        if (formatter != null) {
            if (timestampFormatters == null) {
                timestampFormatters = new ArrayList<>();
            }
            timestampFormatters.add(formatter);
        }
    }

}
