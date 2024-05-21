package com.pw.gwhparser.parser.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.pw.gwhparser.parser.enums.DataType;
import com.pw.gwhparser.parser.enums.ValueType;

import javax.json.JsonNumber;
import javax.json.JsonString;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValueUtil {

    public static final String AMPERSAND = "@";
    public static final String CDATA_START = "<![CDATA[";
    public static final String CDATA_END = "]]>";
    public static final String LIST_SUFFIX = "_LIST";
    private static final String BLANK = "";

    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter DEFAULT_TIMESTAMP_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static final String TYPE_CONVERSION_ERROR = "Cannot get {0}, value: {1}, class: {2}";
    public static final String DATETIME_CONVERSION_ERROR = "Cannot get {0}, value: {1}, formatter: {2}";
    public static final String UNEXPECTED_TYPE_WARNING = "Unexpected type, value: {0}, class: {1}";

    private static boolean isLocalDateTimeEnabled = false;

    static {
        try {
            String enabled = System.getProperty("localdatetime", "false");
            isLocalDateTimeEnabled = Boolean.parseBoolean(enabled);
        } catch (Exception e) {
            log.warn("Cannot get localdatetime, use default value: false", e);
        }
    }

    private ValueUtil() {
        throw new UnsupportedOperationException("Unsupported construction: " + getClass().getName());
    }

    public static BigDecimal getBigDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Double || value instanceof Float) {
            return new BigDecimal(((Number) value).doubleValue());
        }
        if (value instanceof Number) {
            return new BigDecimal(((Number) value).longValue());
        }
        if (value instanceof String) {
            if (NumberUtils.isNumber((String)value)) {
                return NumberUtils.createBigDecimal((String) value);
            } else {
                return new BigDecimal(0);
            }
        }
        if (value instanceof JsonNumber) {
            return ((JsonNumber) value).bigDecimalValue();
        }
        if (value instanceof JsonString) {
            String str = ((JsonString) value).getString();
            return new BigDecimal(str);
        }
        if (value == null) {
            return null;
        }

        throw new IllegalArgumentException(String.format(TYPE_CONVERSION_ERROR, "BigDecimal", value, value.getClass()));
    }

    public static Object getBoolean(Object value) {
        if (value instanceof Boolean) {
            return value;
        }
        if (value instanceof String) {
            return Boolean.valueOf((String) value);
        }
        if (value == null) {
            return null;
        }

        throw new IllegalArgumentException(String.format(TYPE_CONVERSION_ERROR, "Boolean", value, value.getClass()));
    }

    public static Integer getInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).intValue();
        }
        if (value instanceof Number) {
            return Integer.valueOf((String) value);
        }
        if (value instanceof String) {
            return Integer.valueOf((String) value);
        }
        if (value instanceof JsonNumber) {
            return Integer.valueOf(((JsonNumber) value).intValue());
        }
        if (value instanceof JsonString) {
            return Integer.valueOf(((JsonString) value).getString());
        }
        if (value == null) {
            return null;
        }

        throw new IllegalArgumentException(String.format(TYPE_CONVERSION_ERROR, "Integer", value, value.getClass()));
    }

    public static Long getLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).longValue();
        }
        if (value instanceof Number) {
            return Long.valueOf(((Number) value).longValue());
        }
        if (value instanceof String) {
            return Long.valueOf((String) value);
        }
        if (value instanceof JsonNumber) {
            return Long.valueOf(((JsonNumber) value).longValue());
        }
        if (value instanceof JsonString) {
            return Long.valueOf(((JsonString) value).getString());
        }
        if (value == null) {
            return null;
        }
        throw new IllegalArgumentException(String.format(TYPE_CONVERSION_ERROR, "Long", value, value.getClass()));
    }

    public static String getString(Object value) {
        if (value == null) {
            return null;
        }
        String str = null;
        if (value instanceof String) {
            str = ((String) value).trim();
        } else if (value instanceof JsonString) {
            str = ((JsonString) value).getString().trim();
        } else {
            str = getStringForNumeric(value);
        }

        return (str == null || str.isEmpty()) ? null : str;
    }

    public static String getStringForNumeric(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        }
        if (value instanceof Double || value instanceof Float) {
            return BigDecimal.valueOf(((Number) value).doubleValue()).toPlainString();
        }
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).longValue()).toPlainString();
        }
        if (value instanceof JsonNumber) {
            return ((JsonNumber) value).bigDecimalValue().toPlainString();
        }
        return value.toString().trim();
    }

    public static Object getDate(String str, DateTimeFormatter formatter) {
        try {
            return getTemporal(str, formatter, DataType.DATE);
        } catch (Exception e) {
            log.info("DateTime conversion error: " + str + formatter);
            throw e;
        }
    }

    public static Object getDate(String str, List<DateTimeFormatter> formatters) {
        return getTemporal(str, formatters, DataType.DATE);
    }

    public static Object getTimestamp(String str, DateTimeFormatter formatter) {

        try {
            return getTemporal(str, DEFAULT_DATE_FORMATTER, DataType.TIMESTAMP);
        } catch (Exception e) {
            String errorMsg = MessageFormat.format("DateTime Conversion Error", "TIMESTAMP", str, formatter);
            log.info(errorMsg);
            throw e;
        }
    }

    public static Object getTimestamp(String str, List<DateTimeFormatter> formatters) {
        return getTemporal(str, formatters, DataType.TIMESTAMP);
    }

    public static Object getTemporal(String str, List<DateTimeFormatter> formatters, DataType type) {
        if (str == null || formatters == null || formatters.isEmpty()) {
            return str;
        }
        Iterator<DateTimeFormatter> formattersIterator = formatters.iterator();
        int errorCount = 0;
        while (formattersIterator.hasNext()) {
            DateTimeFormatter formatter = formattersIterator.next();
            if (formatter == null) {
                continue;
            }
            try {
                return getTemporal(str, formatter, type);
            } catch (Exception e) {
                errorCount++;
            }
        }

        if (errorCount > 0) {
            String errorMsg = MessageFormat.format("DateTime Conversion Error", type, str, formatters);
            IllegalArgumentException e = new IllegalArgumentException(errorMsg);
            log.info(errorMsg);
            throw e;
        }
        return str;
    }


    protected static Object getTemporal(String str, DateTimeFormatter formatter, DataType type) {
        if (str == null || formatter == null) {
            return str;
        }

        if (DataType.TIMESTAMP.equals(type)) {
            if (isLocalDateTimeEnabled) {
                return LocalDateTime.parse(str, formatter);
            }
            return Date.valueOf(LocalDate.parse(str, formatter));
        }
        throw new IllegalArgumentException("Invalid temporal data type: " + type);
    }

    public static boolean isListValueType(ValueType type) {
        if (type == null) {
            return false;
        }
        return (LIST_SUFFIX != null && type.toString().endsWith(LIST_SUFFIX));
    }

    public static ValueType getSimpleValueType(ValueType type) {
        if (type == null || type == ValueType.ENTITY || type == ValueType.ENTITY_LIST) {
            return null;
        }
        if (type == ValueType.STRING_CONCAT) {
            return ValueType.STRING;
        }
        String typeStr = type.toString();
        if (LIST_SUFFIX != null && typeStr.endsWith(LIST_SUFFIX)) {
            return ValueType.valueOf(typeStr.substring(0, typeStr.length() - LIST_SUFFIX.length()));
        }
        return type;
    }

    public static String getValueString(Object obj) {
        if (obj == null) {
            return BLANK;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).toPlainString();
        }
        if (obj instanceof Integer) {
            return new BigDecimal(((Integer) obj).longValue()).toPlainString();
        }
        if (obj instanceof Long) {
            return new BigDecimal(((Long) obj).longValue()).toPlainString();
        }
        if (obj instanceof LocalDate) {
            return getDateString((LocalDate) obj);
        }
        if (obj instanceof LocalTime) {
            return getTimestampString((LocalDateTime) obj);
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).toString();
        }
        return getValueStringForDeprecated(obj);
    }

    public static String getValueStringForDeprecated(Object obj) {
        if (obj == null) {
            return BLANK;
        }
        Object converted = TypeUtil.convertDeprecated(obj);
        if (converted != null && !converted.equals(obj)) {
            return getValueString(converted);
        }
        String errorMsg = MessageFormat.format("Unsupported value type {0} : {1} ", obj.getClass().getName(), obj);
        IllegalArgumentException e = new IllegalArgumentException(errorMsg);
        log.error(errorMsg, e);
        throw e;
    }

    public static String getDateString(LocalDate date) {
        if (date == null) {
            return BLANK;
        }
        DateTimeFormatter formatter = DEFAULT_DATE_FORMATTER;
        ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneId.systemDefault());
        return zonedDateTime.format(formatter);
    }

    public static String getTimestampString(LocalDateTime timestamp) {
        if (timestamp == null) {
            return BLANK;
        }
        DateTimeFormatter formatter = DEFAULT_TIMESTAMP_FORMATTER;
        ZonedDateTime zonedDateTime = timestamp.atZone(ZoneId.systemDefault());
        return zonedDateTime.format(formatter);
    }
}
