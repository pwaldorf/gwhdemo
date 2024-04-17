package com.pw.gwhparser.parser.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TypeUtil {

    public static Object convertDeprecated(Object obj) {
        if (obj instanceof Double) {
            return BigDecimal.valueOf(((Double) obj).doubleValue());
        }
        if (obj instanceof java.sql.Timestamp) {
            return getLocalDateTime((java.sql.Timestamp) obj);
        }
        if (obj instanceof java.util.Date) {
            return getLocalDate((java.util.Date) obj);
        }
        return obj;
    }

    protected static LocalDateTime getLocalDateTime(java.sql.Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    protected static LocalDate getLocalDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
