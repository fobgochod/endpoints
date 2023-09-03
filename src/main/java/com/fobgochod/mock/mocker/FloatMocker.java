package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * Float对象模拟器
 */
public class FloatMocker implements Mocker<Float> {

    @Override
    public Float mock(MockConfig config) {
        return RandomUtils.nextFloat(config.getFloatRange()[0], config.getFloatRange()[1]);
    }

}
