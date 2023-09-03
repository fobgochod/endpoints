package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * Integer对象模拟器
 */
public class IntegerMocker implements Mocker<Integer> {

    @Override
    public Integer mock(MockConfig config) {
        return RandomUtils.nextInt(config.getIntRange()[0], config.getIntRange()[1]);
    }
}
