package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * Boolean对象模拟器
 */
public class BooleanMocker implements Mocker<Boolean> {

    @Override
    public Boolean mock(MockConfig mockConfig) {
        return RandomUtils.nextBoolean();
    }
}
