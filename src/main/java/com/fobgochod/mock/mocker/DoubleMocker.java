package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * Double对象模拟器
 */
public class DoubleMocker implements Mocker<Double> {

    @Override
    public Double mock(MockConfig config) {
        return RandomUtils.nextDouble(config.getDoubleRange()[0], config.getDoubleRange()[1]);
    }

}
