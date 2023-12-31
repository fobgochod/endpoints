package com.fobgochod.mock;

/**
 * 模拟器接口
 */
public interface Mocker<T> {

    /**
     * 模拟数据
     *
     * @param config 模拟数据配置
     * @return 模拟数据对象
     */
    T mock(MockConfig config);
}
