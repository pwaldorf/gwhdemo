package com.pw.gwhparser1.parser.constants;

import com.pw.gwhparser1.parser.enums.ParserFormat;

public class ParserConstants {

    public static final String DIRECTIVE_ENTRY = "Entity";
    public static final String DIRECTIVE_ENTRY_LIST = "EntityList";
    public static final String DIRECTIVE_STRING_CONCAT = "StringConcat";

    public static final String ELEMENT_DELIM = ",";
    public static final String ROOT = "ROOT";
    public static final String SAMPLE_CHARSET_NAME = "UTF-8";

    public static final ParserFormat DEFAULT_PARSER_FORMAT = ParserFormat.XML;
    public static final String DEFAULT_CHARACTER_SET = "UTF-8";
    public static final String DEFAULT_ROOT_ENTITY_NAME = "ROOT";
    public static final String DEFAULT_CONCAT_DELIMITER = " ";

    public static final String PREFIX_SEPARATOR = ":";
    public static final String GBM_NAMESPACE_PREFIX = "http://www.statestr.com/gce_gbm";
    public static final String XMLNS = "xmnls";
    public static final String HTTP = "http";

    public static final String PATH_DELIM = "/";
    public static final String BLANK = "";

}
