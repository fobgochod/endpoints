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
    }

    // @Test
    // public void testGenericData() {
    //     // 模拟集合实体
    //     List<BasicBean> basicBeanList = Mock.mock(new TypeReference<List<BasicBean>>() {
    //     });
    //     System.err.println(JsonUtils.toJson(basicBeanList));
    //
    //     // 各种组合忽略。。。。map同理。下面模拟一个不知道什么类型的map
    //     Map<List<Map<Integer, String[][]>>, Map<Set<String>, Double[]>> some = Mock
    //             .mock(new TypeReference<Map<List<Map<Integer, String[][]>>, Map<Set<String>, Double[]>>>() {
    //             });
    //     System.err.println(JsonUtils.toJson(some));
    //
    //     Mock.mock(new TypeReference<GenericData<Integer, String, BasicBean>>() {
    //     });
    // }

    @Test
    public void testMockConfig() {
        MockConfig config = new MockConfig()
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
        BasicBean basicBean = Mock.mock(BasicBean.class, config);
        System.out.println(JsonUtils.toJson(basicBean));
    }

    @Test
    public void testBasicData() {
        BasicBean basicBean = Mock.mock(BasicBean.class);
        System.out.println(JsonUtils.toJson(basicBean));
    }

    @Test
    public void testIgnore() {
        IgnoreBean ignoreBean = Mock.mock(IgnoreBean.class);
        System.err.println(JsonUtils.toJson(ignoreBean));
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
        Type superClass = ArrayList.class.getGenericSuperclass();
        System.out.println(superClass instanceof ParameterizedType);

        Type genericSuperclass = int.class.getGenericSuperclass();
        System.out.println(genericSuperclass instanceof ParameterizedType);
    }
}
