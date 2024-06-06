package com.pw.gwhparser1.parser.enums;


import java.util.List;

import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.util.ValueUtil;

import java.time.format.DateTimeFormatter;

public enum ValueType {

    STRING {
        @Override
        public Object getValue(Object value, GenParserConfig config) {
            return ValueUtil.getString(value);
		}
    }, STRING_CONCAT {
        @Override
        public Object getValue(Object value, GenParserConfig config) {
            return ValueUtil.getString(value);
		}
    }, BIGDECIMAL {
        @Override
        public Object getValue(Object value, GenParserConfig config) {
            return ValueUtil.getBigDecimal(value);
		}
    }, LONG {
        @Override
        public Object getValue(Object value, GenParserConfig config) {
            return ValueUtil.getLong(value);
		}
    }, INTEGER {
        @Override
        public Object getValue(Object value, GenParserConfig config) {
            return ValueUtil.getInteger(value);
		}
    }, DATE {
        @Override
        public Object getValue(Object value, GenParserConfig config) {
            return getDate(ValueUtil.getString(value), config);
		}
    }, TIMESTAMP {
        @Override
        public Object getValue(Object value, GenParserConfig config) {
            return getTimestamp(ValueUtil.getString(value), config);
		}
    }, BOOLEAN {
        @Override
        public Object getValue(Object value, GenParserConfig config) {
            return ValueUtil.getBoolean(value);
		}
    }, BIGDECIMAL_LIST,
    BOOLEAN_LIST, DATE_LIST, ENTITY,
    ENTITY_LIST, INTEGER_LIST,
    LONG_LIST, STRING_LIST,
    TIMESTAMP_LIST;

    public Object getValue(Object value) {
        return getValue(value, null);
    }

    public Object getValue(Object value, GenParserConfig config) {
        throw new IllegalArgumentException("Unsupported type: " + this);
    }

    protected Object getDate(String str, GenParserConfig config) {
        List<DateTimeFormatter> formatters = (config != null ? config.getDateFormatters() : null);
        if (formatters != null) {
            return ValueUtil.getDate(str, formatters);
        }
        return ValueUtil.getDate(str, ValueUtil.DEFAULT_DATE_FORMATTER);
    }

    protected Object getTimestamp(String str, GenParserConfig config) {
        List<DateTimeFormatter> formatters = (config != null ? config.getTimestampFormatters() : null);
        if (formatters != null) {
            return ValueUtil.getTimestamp(str, formatters);
        }
        return ValueUtil.getTimestamp(str, ValueUtil.DEFAULT_TIMESTAMP_FORMATTER);
    }

}
