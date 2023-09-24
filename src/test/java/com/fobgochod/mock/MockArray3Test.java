package com.fobgochod.mock;

import com.fobgochod.mock.bean.enums.DayEnum;
import com.fobgochod.mock.util.JsonUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class MockArray3Test {

    @Test
    public void testArray3() {
        // 基本类型 二维数组 模拟
        byte[][][] byte1 = Mock.mock(byte[][][].class);
        System.err.println(JsonUtils.toJson(byte1));

        short[][][] short1 = Mock.mock(short[][][].class);
        System.err.println(JsonUtils.toJson(short1));

        int[][][] int1 = Mock.mock(int[][][].class);
        System.err.println(JsonUtils.toJson(int1));

        long[][][] long1 = Mock.mock(long[][][].class);
        System.err.println(JsonUtils.toJson(long1));

        double[][][] double1 = Mock.mock(double[][][].class);
        System.err.println(JsonUtils.toJson(double1));

        float[][][] float1 = Mock.mock(float[][][].class);
        System.err.println(JsonUtils.toJson(float1));

        char[][][] char1 = Mock.mock(char[][][].class);
        System.err.println(JsonUtils.toJson(char1));

        boolean[][][] boolean1 = Mock.mock(boolean[][][].class);
        System.err.println(JsonUtils.toJson(boolean1));

        // 基本类型封装类模拟
        Byte[][][] byte2 = Mock.mock(Byte[][][].class);
        System.err.println(JsonUtils.toJson(byte2));

        Short[][][] short2 = Mock.mock(Short[][][].class);
        System.err.println(JsonUtils.toJson(short2));

        Integer[][][] int2 = Mock.mock(Integer[][][].class);
        System.err.println(JsonUtils.toJson(int2));

        Long[][][] long2 = Mock.mock(Long[][][].class);
        System.err.println(JsonUtils.toJson(long2));

        Double[][][] double2 = Mock.mock(Double[][][].class);
        System.err.println(JsonUtils.toJson(double2));

        Float[][][] float2 = Mock.mock(Float[][][].class);
        System.err.println(JsonUtils.toJson(float2));

        Character[][][] char2 = Mock.mock(Character[][][].class);
        System.err.println(JsonUtils.toJson(char2));

        Boolean[][][] boolean2 = Mock.mock(Boolean[][][].class);
        System.err.println(JsonUtils.toJson(boolean2));

        // 常用类型 数组 模拟
        // 字符串
        String[][][] string = Mock.mock(String[][][].class);
        System.err.println(JsonUtils.toJson(string));
        // 大精度
        BigDecimal[][][] bigDecimal = Mock.mock(BigDecimal[][][].class);
        System.err.println(JsonUtils.toJson(bigDecimal));
        // 大整形
        BigInteger[][][] bigInteger = Mock.mock(BigInteger[][][].class);
        System.out.println(JsonUtils.toJson(bigInteger));
        // 日期
        Date[][][] date = Mock.mock(Date[][][].class);
        System.out.println(JsonUtils.toJson(date));
        // 枚举
        DayEnum[][][] dayEnum = Mock.mock(DayEnum[][][].class);
        System.out.println(JsonUtils.toJson(dayEnum));
    }
}
