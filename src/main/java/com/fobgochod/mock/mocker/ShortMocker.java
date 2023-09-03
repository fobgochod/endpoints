package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * 模拟Short对象
 */
public class ShortMocker implements Mocker<Short> {

    @Override
    public Short mock(MockConfig config) {
        return (short) RandomUtils.nextInt(config.getShortRange()[0], config.getShortRange()[1]);
    }
}
