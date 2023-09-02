package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * Float对象模拟器
 */
public class FloatMocker implements Mocker<Float> {

    @Override
    public Float mock(MockConfig mockConfig) {
        return RandomUtils.nextFloat(mockConfig.getFloatRange()[0], mockConfig.getFloatRange()[1]);
    }

}
