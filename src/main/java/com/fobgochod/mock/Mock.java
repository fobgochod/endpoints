package com.fobgochod.mock;

import com.fobgochod.mock.mocker.BaseMocker;

/**
 * <a href="https://github.com/dakuohao/mock">模拟对象门面类</a>
 */
public class Mock {

    /**
     * 模拟数据
     *
     * @param clazz 模拟数据类型
     * @return 模拟数据对象
     */
    public static <T> T mock(Class<T> clazz) {
        return mock(clazz, new MockConfig());
    }

    /**
     * 模拟数据
     *
     * @param clazz  模拟数据类型
     * @param config 模拟数据配置
     * @return 模拟数据对象
     */
    public static <T> T mock(Class<T> clazz, MockConfig config) {
        return new BaseMocker<T>(clazz).mock(config);
    }

    /**
     * 模拟数据
     * <pre>
     * 注意typeReference必须以{}结尾
     * </pre>
     *
     * @param typeReference 模拟数据包装类型
     * @return 模拟数据对象
     */
    public static <T> T mock(TypeReference<T> typeReference) {
        return mock(typeReference, new MockConfig());
    }

    /**
     * 模拟数据
     * <pre>
     * 注意typeReference必须以{}结尾
     * </pre>
     *
     * @param typeReference 模拟数据类型
     * @param config        模拟数据配置
     * @return 模拟数据对象
     */
    public static <T> T mock(TypeReference<T> typeReference, MockConfig config) {
        return new BaseMocker<T>(typeReference.getType()).mock(config.init(typeReference.getType()));
    }

}
