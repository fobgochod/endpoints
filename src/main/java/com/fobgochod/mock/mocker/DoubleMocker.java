package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * Double对象模拟器
 */
public class DoubleMocker implements Mocker<Double> {

    @Override
    public Double mock(MockConfig mockConfig) {
        return RandomUtils.nextDouble(mockConfig.getDoubleRange()[0], mockConfig.getDoubleRange()[1]);
    }

}
