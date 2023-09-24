package com.fobgochod.mock;

import com.fobgochod.mock.bean.enums.DayEnum;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class MockBaseTest {

    @Test
    public void testPrimitive() {
        byte aByte = Mock.mock(byte.class);
        System.err.println("byte: " + aByte);

        boolean aBoolean = Mock.mock(boolean.class);
        System.err.println("boolean: " + aBoolean);

        char aChar = Mock.mock(char.class);
        System.err.println("char: " + aChar);

        short aShort = Mock.mock(short.class);
        System.err.println("short: " + aShort);

        int aInt = Mock.mock(int.class);
        System.err.println("int: " + aInt);

        long aLong = Mock.mock(long.class);
        System.err.println("long: " + aLong);

        float aFloat = Mock.mock(float.class);
        System.err.println("float: " + aFloat);

        double aDouble = Mock.mock(double.class);
        System.err.println("double: " + aDouble);
    }

    @Test
    public void testReference() {
        Byte byte2 = Mock.mock(Byte.class);
        System.err.println("Byte: " + byte2);

        Boolean boolean2 = Mock.mock(Boolean.class);
        System.err.println("Boolean: " + boolean2);

        Character char2 = Mock.mock(Character.class);
        System.err.println("Character: " + char2);

        Short short2 = Mock.mock(Short.class);
        System.err.println("Short: " + short2);

        Integer int2 = Mock.mock(Integer.class);
        System.err.println("Integer: " + int2);

        Long long2 = Mock.mock(Long.class);
        System.err.println("Long: " + long2);

        Float float2 = Mock.mock(Float.class);
        System.err.println("Float: " + float2);

        Double double2 = Mock.mock(Double.class);
        System.err.println("Double: " + double2);
    }

    @Test
    public void testCommon() {
        String string = Mock.mock(String.class);
        System.err.println("String: " + string);

        BigInteger bigInteger = Mock.mock(BigInteger.class);
        System.out.println("BigInteger: " + bigInteger);

        BigDecimal bigDecimal = Mock.mock(BigDecimal.class);
        System.err.println("BigDecimal: " + bigDecimal);

        Date date = Mock.mock(Date.class);
        System.out.println("Date: " + date);

        LocalDate localDate = Mock.mock(LocalDate.class);
        System.out.println("LocalDate: " + localDate);

        LocalTime localTime = Mock.mock(LocalTime.class);
        System.out.println("LocalTime: " + localTime);

        LocalDateTime localDateTime = Mock.mock(LocalDateTime.class);
        System.out.println("LocalDateTime: " + localDateTime);

        Timestamp timestamp = Mock.mock(Timestamp.class);
        System.out.println("Timestamp: " + timestamp);
    }

    @Test
    public void testEnum() {
        DayEnum dayEnum = Mock.mock(DayEnum.class);
        System.out.println("DayEnum: " + dayEnum);
    }
}
