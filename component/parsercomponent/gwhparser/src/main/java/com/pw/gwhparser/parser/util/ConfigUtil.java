package com.pw.gwhparser.parser.util;

import java.time.format.DateTimeFormatter;

import com.pw.gwhparser.parser.constants.ParserConstants;
import com.pw.gwhparser.parser.model.Entity;
import com.pw.gwhparser.parser.model.SimpleEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigUtil {

    private static final String BASIC_ISO_DATE = "BASIC_ISO_DATE";
    private static final String ISO_DATE = "ISO_DATE";
    private static final String ISO_LOCAL_DATE = "ISO_LOCAL_DATE";
    private static final String ISO_OFFSET_DATE = "ISO_OFFSET_DATE";
    private static final String ISO_ORDINAL_DATE = "ISO_ORDINAL_DATE";
    private static final String ISO_WEEK_DATE = "ISO_WEEK_DATE";
    private static final String ISO_DATE_TIME = "ISO_DATE_TIME";
    private static final String ISO_INSTANT = "ISO_INSTANT";
    private static final String ISO_LOCAL_DATE_TIME = "ISO_LOCAL_DATE_TIME";
    private static final String ISO_OFFSET_DATE_TIME = "ISO_OFFSET_DATE_TIME";
    private static final String ISO_ZONED_DATE_TIME = "ISO_ZONED_DATE_TIME";
    private static final String RFC_1123_DATE_TIME = "RFC_1123_DATE_TIME";

    private static final Object APPKEY = "APPKEY";
    private static final String BLANK = "";
    private static final String LITERAL = "literal";
    private static final String COND = "cond";


    private ConfigUtil() {
        throw new UnsupportedOperationException("Unsupported Construction " + getClass().getName());
    }

    public static DateTimeFormatter getDateFormatter(String format) {
        DateTimeFormatter formatter = null;
        if (format.equalsIgnoreCase(BASIC_ISO_DATE)) {
            formatter = DateTimeFormatter.BASIC_ISO_DATE;
        } else if (format.equalsIgnoreCase(ISO_DATE)) {
            formatter = DateTimeFormatter.ISO_DATE;
        }  else if (format.equalsIgnoreCase(ISO_LOCAL_DATE)) {
            formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        } else if (format.equalsIgnoreCase(ISO_OFFSET_DATE)) {
            formatter = DateTimeFormatter.ISO_OFFSET_DATE;
        } else if (format.equalsIgnoreCase(ISO_ORDINAL_DATE)) {
            formatter = DateTimeFormatter.ISO_ORDINAL_DATE;
        } else if (format.equalsIgnoreCase(ISO_WEEK_DATE)) {
            formatter = DateTimeFormatter.ISO_WEEK_DATE;
        } else {
            try {
                formatter = DateTimeFormatter.ofPattern(format);
            } catch (Exception e) {
                log.error("Cannot create DateTimeFormatter for pattern " + format, e);
            }
        }
        return formatter;
    }

    public static DateTimeFormatter getTimestampFormatter(String format) {
        DateTimeFormatter formatter = null;
        if (format.equalsIgnoreCase(ISO_DATE_TIME)) {
            formatter = DateTimeFormatter.ISO_DATE_TIME;
        } else if (format.equalsIgnoreCase(ISO_INSTANT)) {
            formatter = DateTimeFormatter.ISO_INSTANT;
        }  else if (format.equalsIgnoreCase(ISO_LOCAL_DATE_TIME)) {
            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        } else if (format.equalsIgnoreCase(ISO_OFFSET_DATE_TIME)) {
            formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        } else if (format.equalsIgnoreCase(ISO_ZONED_DATE_TIME)) {
            formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        } else if (format.equalsIgnoreCase(RFC_1123_DATE_TIME)) {
            formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        } else {
            try {
                formatter = DateTimeFormatter.ofPattern(format);
            } catch (Exception e) {
                log.error("Cannot create TimestampFormatter for pattern " + format, e);
            }
        }
        return formatter;
    }

    public static String getElementValueString(String appKey, Entity entity, String elementConfig) {

        boolean isCDATA = false;
        String elementName = elementConfig;
        if (hasCDATATags(elementName)) {
            isCDATA = true;
            elementName = removeCDDATATags(elementName);
        }
        Object value = null;
        if (hasLiteralPrefix(elementName)) {
            value = getSuffix(elementName, ParserConstants.PREFIX_SEPARATOR);
        } else {
            value = (APPKEY.equals(elementName) ? appKey : entity.getByPath(elementName));
        }
        if (null == value) {
            value = SimpleEntity.getFirstAppearence(entity, elementName);
        }
        if (value != null && isCDATA) {
            StringBuilder sb = new StringBuilder();
            sb.append(ValueUtil.CDATA_START).append(value).append(ValueUtil.CDATA_END);
            value = sb.toString();
        }
        return (value instanceof String ? (String) value : ValueUtil.getValueString(value));
    }

    public static String getPrefix(String str, String separator) {
        if (str == null || str.isEmpty()) {
            return BLANK;
        }
        int index = str.indexOf(separator);
        if (index == -1) {
            return BLANK;
        }
        return str.substring(0, index).trim();
    }

    public static String getSuffix(String str, String separator) {
        if (str == null || str.isEmpty()) {
            return BLANK;
        }
        int index = str.indexOf(separator);
        if (index == -1) {
            return str.trim();
        }
        return str.substring(index + 1).trim();
    }

    public static boolean hasLiteralPrefix(String str) {
        String prefix = getPrefix(str, ParserConstants.PREFIX_SEPARATOR);
        return (prefix != null && prefix.toLowerCase().indexOf(LITERAL) != -1);
    }

    public static boolean hasConditionalPrefix(String str) {
        String prefix = getPrefix(str, ParserConstants.PREFIX_SEPARATOR);
        return (prefix != null && prefix.toLowerCase().indexOf(COND) != -1);
    }

    public static boolean hasCDATATags(String str) {
        return (str != null && str.indexOf(ValueUtil.CDATA_START) != -1 && str.indexOf(ValueUtil.CDATA_END) != -1);
    }

    public static String removeCDDATATags(String str) {
        if (hasCDATATags(str)) {
            return str.replace(ValueUtil.CDATA_START, BLANK).replace(ValueUtil.CDATA_END, BLANK);
        }
        return str;
    }

}
