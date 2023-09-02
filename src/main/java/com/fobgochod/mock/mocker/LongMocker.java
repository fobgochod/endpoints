package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * 模拟Long对象
 */
public class LongMocker implements Mocker<Long> {

    @Override
    public Long mock(MockConfig mockConfig) {
        return RandomUtils.nextLong(mockConfig.getLongRange()[0], mockConfig.getLongRange()[1]);
    }
}
