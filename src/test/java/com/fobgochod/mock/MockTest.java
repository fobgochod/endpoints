package com.fobgochod.mock;

import com.fobgochod.mock.bean.*;
import com.fobgochod.mock.bean.circular.AXB;
import com.fobgochod.mock.bean.enums.DayEnum;
import com.fobgochod.mock.enums.StringType;
import com.fobgochod.mock.util.JsonUtils;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.assertSame;

public class MockTest {

    @Test
    public void testJson(){
        String json = JsonUtils.toJson(new BasicBean());
        System.out.println("json = " + json);
    }

    @Test
    public void testString() {
        for (int i = 1; i <= 10; i++) {
            System.err.println(i + ": " + Mock.mock(String.class));
        }
    }

    /**
     * java基本类型模拟测试
     */
    @Test
    public void testBasic() {
        // test primitive
        byte byte1 = Mock.mock(byte.class);
        System.err.println("byte: " + byte1);

        short short1 = Mock.mock(short.class);
        System.err.println("short: " + short1);

        int int1 = Mock.mock(int.class);
        System.err.println("int: " + int1);

        long long1 = Mock.mock(long.class);
        System.err.println("long: " + long1);

        double double1 = Mock.mock(double.class);
        System.err.println("double: " + double1);

        float float1 = Mock.mock(float.class);
        System.err.println("float: " + float1);

        char char1 = Mock.mock(char.class);
        System.err.println("char: " + char1);

        boolean boolean1 = Mock.mock(boolean.class);
        System.err.println("boolean: " + boolean1);

        // test reference
        Byte byte2 = Mock.mock(Byte.class);
        System.err.println("Byte: " + byte2);

        Short short2 = Mock.mock(Short.class);
        System.err.println("Short: " + short2);

        Integer int2 = Mock.mock(Integer.class);
        System.err.println("Integer: " + int2);

        Long long2 = Mock.mock(Long.class);
        System.err.println("Long: " + long2);

        Double double2 = Mock.mock(Double.class);
        System.err.println("Double: " + double2);

        Float float2 = Mock.mock(Float.class);
        System.err.println("Float: " + float2);

        Character char2 = Mock.mock(Character.class);
        System.err.println("Character: " + char2);

        Boolean boolean2 = Mock.mock(Boolean.class);
        System.err.println("Boolean: " + boolean2);

        // 常用类型模拟
        // 字符串
        String string = Mock.mock(String.class);
        System.err.println("String: " + string);
        // 大精度
        BigDecimal bigDecimal = Mock.mock(BigDecimal.class);
        System.err.println("BigDecimal: " + bigDecimal);
        // 大整形
        BigInteger bigInteger = Mock.mock(BigInteger.class);
        System.out.println("BigInteger: " + bigInteger);
        // 日期
        Date date = Mock.mock(Date.class);
        System.out.println("Date: " + date);
        LocalTime localTime = Mock.mock(LocalTime.class);
        System.out.println("LocalTime: " + localTime);
        LocalDate localDate = Mock.mock(LocalDate.class);
        System.out.println("LocalDate: " + localDate);
        LocalDateTime localDateTime = Mock.mock(LocalDateTime.class);
        System.out.println("LocalDateTime: " + localDateTime);

        // 枚举
        DayEnum dayEnum = Mock.mock(DayEnum.class);
        System.out.println("DayEnum: " + dayEnum);
    }

    @Test
    public void testEnum() {
        // 枚举
        DayEnum dayEnum = Mock.mock(DayEnum.class);
        System.out.println("DayEnum: " + dayEnum);
    }

    /**
     * 基本类型的数组类型模拟测试
     */
    @Test
    public void testArray() {

        // 基本类型 数组 模拟
        byte[] byte1 = Mock.mock(byte[].class);
        System.err.println(JsonUtils.toJson(byte1));// byte转json 这里做了new string 打印是string
        // 循环打印出来
        for (byte b : byte1) {
            System.out.println(b);
        }

        short[] short1 = Mock.mock(short[].class);
        System.err.println(JsonUtils.toJson(short1));

        int[] int1 = Mock.mock(int[].class);
        System.err.println(JsonUtils.toJson(int1));

        long[] long1 = Mock.mock(long[].class);
        System.err.println(JsonUtils.toJson(long1));

        double[] double1 = Mock.mock(double[].class);
        System.err.println(JsonUtils.toJson(double1));

        float[] float1 = Mock.mock(float[].class);
        System.err.println(JsonUtils.toJson(float1));

        char[] char1 = Mock.mock(char[].class);
        System.err.println(JsonUtils.toJson(char1));

        boolean[] boolean1 = Mock.mock(boolean[].class);
        System.err.println(JsonUtils.toJson(boolean1));

        // 基本类型封装类模拟
        Byte[] byte2 = Mock.mock(Byte[].class);
        System.err.println(JsonUtils.toJson(byte2));

        Short[] short2 = Mock.mock(Short[].class);
        System.err.println(JsonUtils.toJson(short2));

        Integer[] int2 = Mock.mock(Integer[].class);
        System.err.println(JsonUtils.toJson(int2));

        Long[] long2 = Mock.mock(Long[].class);
        System.err.println(JsonUtils.toJson(long2));

        Double[] double2 = Mock.mock(Double[].class);
        System.err.println(JsonUtils.toJson(double2));

        Float[] float2 = Mock.mock(Float[].class);
        System.err.println(JsonUtils.toJson(float2));

        Character[] char2 = Mock.mock(Character[].class);
        System.err.println(JsonUtils.toJson(char2));

        Boolean[] boolean2 = Mock.mock(Boolean[].class);
        System.err.println(JsonUtils.toJson(boolean2));

        // 常用类型 数组 模拟
        // 字符串
        String[] string = Mock.mock(String[].class);
        System.err.println(JsonUtils.toJson(string));
        // 大精度
        BigDecimal[] bigDecimal = Mock.mock(BigDecimal[].class);
        System.err.println(JsonUtils.toJson(bigDecimal));
        // 大整形
        BigInteger[] bigInteger = Mock.mock(BigInteger[].class);
        System.out.println(JsonUtils.toJson(bigInteger));
        // 日期
        Date[] date = Mock.mock(Date[].class);
        System.out.println(JsonUtils.toJson(date));
        // 枚举
        DayEnum[] dayEnum = Mock.mock(DayEnum[].class);
        System.out.println(JsonUtils.toJson(dayEnum));
    }


    /**
     * 基本类型的二维数组模拟测试
     */
    @Test
    public void testArray2() {

        // 基本类型 二维数组 模拟
        byte[][] byte1 = Mock.mock(byte[][].class);
        System.err.println(JsonUtils.toJson(byte1));

        short[][] short1 = Mock.mock(short[][].class);
        System.err.println(JsonUtils.toJson(short1));

        int[][] int1 = Mock.mock(int[][].class);
        System.err.println(JsonUtils.toJson(int1));

        long[][] long1 = Mock.mock(long[][].class);
        System.err.println(JsonUtils.toJson(long1));

        double[][] double1 = Mock.mock(double[][].class);
        System.err.println(JsonUtils.toJson(double1));

        float[][] float1 = Mock.mock(float[][].class);
        System.err.println(JsonUtils.toJson(float1));

        char[][] char1 = Mock.mock(char[][].class);
        System.err.println(JsonUtils.toJson(char1));

        boolean[][] boolean1 = Mock.mock(boolean[][].class);
        System.err.println(JsonUtils.toJson(boolean1));

        // 基本类型封装类模拟
        Byte[][] byte2 = Mock.mock(Byte[][].class);
        System.err.println(JsonUtils.toJson(byte2));

        Short[][] short2 = Mock.mock(Short[][].class);
        System.err.println(JsonUtils.toJson(short2));

        Integer[][] int2 = Mock.mock(Integer[][].class);
        System.err.println(JsonUtils.toJson(int2));

        Long[][] long2 = Mock.mock(Long[][].class);
        System.err.println(JsonUtils.toJson(long2));

        Double[][] double2 = Mock.mock(Double[][].class);
        System.err.println(JsonUtils.toJson(double2));

        Float[][] float2 = Mock.mock(Float[][].class);
        System.err.println(JsonUtils.toJson(float2));

        Character[][] char2 = Mock.mock(Character[][].class);
        System.err.println(JsonUtils.toJson(char2));

        Boolean[][] boolean2 = Mock.mock(Boolean[][].class);
        System.err.println(JsonUtils.toJson(boolean2));

        // 常用类型 数组 模拟
        // 字符串
        String[][] string = Mock.mock(String[][].class);
        System.err.println(JsonUtils.toJson(string));
        // 大精度
        BigDecimal[][] bigDecimal = Mock.mock(BigDecimal[][].class);
        System.err.println(JsonUtils.toJson(bigDecimal));
        // 大整形
        BigInteger[][] bigInteger = Mock.mock(BigInteger[][].class);
        System.out.println(JsonUtils.toJson(bigInteger));
        // 日期
        Date[][] date = Mock.mock(Date[][].class);
        System.out.println(JsonUtils.toJson(date));
        // 枚举
        DayEnum[][] dayEnum = Mock.mock(DayEnum[][].class);
        System.out.println(JsonUtils.toJson(dayEnum));
    }

    /**
     * 基本类型的多维数组测试
     */
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

    /**
     * 任意类型
     * 注意TypeReference要加{}才能模拟
     */
    @Test
    public void testTypeReference() {
        // 模拟基础类型，不建议使用这种方式，参考基础类型章节直接模拟。
        Integer integerNum = Mock.mock(new TypeReference<Integer>() {
        });
        System.err.println(JsonUtils.toJson(integerNum));
        Integer[] integerArray = Mock.mock(new TypeReference<Integer[]>() {
        });
        System.err.println(JsonUtils.toJson(integerArray));
        // 模拟集合
        List<Integer> integerList = Mock.mock(new TypeReference<List<Integer>>() {
        });
        System.err.println(JsonUtils.toJson(integerList));
        // 模拟数组集合
        List<Integer[]> integerArrayList = Mock.mock(new TypeReference<List<Integer[]>>() {
        });
        System.err.println(JsonUtils.toJson(integerArrayList));
        // 模拟集合数组
        List<Integer>[] integerListArray = Mock.mock(new TypeReference<List<Integer>[]>() {
        });
        System.err.println(JsonUtils.toJson(integerListArray));
        // 模拟集合实体
        List<BasicBean> basicBeanList = Mock.mock(new TypeReference<List<BasicBean>>() {
        });
        System.err.println(JsonUtils.toJson(basicBeanList));
        // 各种组合忽略。。。。map同理。下面模拟一个不知道什么类型的map
        Map<List<Map<Integer, String[][]>>, Map<Set<String>, Double[]>> some = Mock
                .mock(new TypeReference<Map<List<Map<Integer, String[][]>>, Map<Set<String>, Double[]>>>() {
                });
        System.err.println(JsonUtils.toJson(some));
    }

    @Test
    public void testGenericData() {
        Mock.mock(new TypeReference<GenericData<Integer, String, BasicBean>>() {
        });
    }

    @Test
    public void testMockConfig() {
        MockConfig mockConfig = new MockConfig()
                .byteRange((byte) 0, Byte.MAX_VALUE)
                .shortRange((short) 0, Short.MAX_VALUE)
                .intRange(0, Integer.MAX_VALUE)
                .floatRange(0.0f, Float.MAX_VALUE)
                .doubleRange(0.0, Double.MAX_VALUE)
                .longRange(0, Long.MAX_VALUE)
                .dateRange("1949-01-01", "2049-12-31")
                .sizeRange(5, 10)
                .stringSeed("a", "b", "c")
                .charSeed((char) 97, (char) 98);
        BasicBean basicBean = Mock.mock(BasicBean.class, mockConfig);
        System.out.println(JsonUtils.toJson(basicBean));

    }


    @Test
    public void testBasicData() {
        BasicBean basicBean = Mock.mock(BasicBean.class);
        System.out.println(JsonUtils.toJson(basicBean));
    }

    /**
     * 测试忽略属性
     */
    @Test
    public void testIgnore() {
        IgnoreBean a = Mock.mock(IgnoreBean.class);
        System.err.println(JsonUtils.toJson(a));
    }


    @Test
    public void testCircular() {
        MockConfig mockConfig = new MockConfig().setEnabledCircle(true);
        AXB axb = Mock.mock(AXB.class, mockConfig);
        AXB circularAxb = axb.getBXA().getAXB();
        assertSame(axb, circularAxb);
    }

    @Test
    public void testSelf() {
        MockConfig mockConfig = new MockConfig().setEnabledCircle(true);
        SelfRefData selfRefData = Mock.mock(SelfRefData.class, mockConfig);
        assertSame(selfRefData.getParent(), selfRefData);
    }

    /**
     * 测试返回汉语句子
     */
    @Test
    public void testChineseString() {
        String mock = Mock.mock(String.class);
        System.out.println(mock);
    }


    /**
     * 测试返回汉语句子
     */
    @Test
    public void testChineseStringBean() {
        StringBean mock = Mock.mock(StringBean.class);
        System.out.println(JsonUtils.toJson(mock));
    }

    /**
     * 测试返回汉语句子
     */
    @Test
    public void testUUID() {
        StringBean mock = Mock.mock(StringBean.class, new MockConfig().setStringType(StringType.UUID));
        System.out.println(JsonUtils.toJson(mock));
    }


    // 测试识别 是否为 泛型类型
    @Test
    public void isParamType() {
        Type superClass = new ArrayList<String>().getClass().getGenericSuperclass();
        System.out.println(superClass instanceof ParameterizedType);

        Type genericSuperclass = int.class.getGenericSuperclass();
        System.out.println(genericSuperclass instanceof ParameterizedType);
    }
}
