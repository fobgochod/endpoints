package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * Integer对象模拟器
 */
public class IntegerMocker implements Mocker<Integer> {

    @Override
    public Integer mock(MockConfig mockConfig) {
        return RandomUtils.nextInt(mockConfig.getIntRange()[0], mockConfig.getIntRange()[1]);
    }
}
