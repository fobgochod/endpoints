package com.fobgochod.mock.enums;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public enum BaseType {

    byte_(byte.class),
    boolean_(boolean.class),
    char_(char.class),
    short_(short.class),
    int_(int.class),
    long_(long.class),
    float_(float.class),
    double_(double.class),

    Byte_(Byte.class),
    Boolean_(Boolean.class),
    Character_(Character.class),
    Short_(Short.class),
    Integer_(Integer.class),
    Long_(Long.class),
    Float_(Float.class),
    Double_(Double.class),

    String_(String.class),
    BigInteger_(BigInteger.class),
    BigDecimal_(BigDecimal.class),
    Date_(Date.class),
    TimeUnit_(TimeUnit.class),
    LocalDate_(LocalDate.class),
    LocalTime_(LocalTime.class),
    LocalDateTime_(LocalDateTime.class),
    Timestamp_(Timestamp.class),
    ;

    private final Class<?> clazz;

    BaseType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static boolean contains(String c) {
        return Arrays.stream(values()).anyMatch(value -> value.canonical().equals(c));
    }

    public static BaseType get(String c) {
        return Arrays.stream(values()).filter(value -> value.canonical().equals(c)).findFirst().orElse(null);
    }

    public String canonical() {
        return clazz.getName();
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
