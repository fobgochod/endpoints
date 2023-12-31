package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * Byte对象模拟器
 */
public class ByteMocker implements Mocker<Byte> {

    @Override
    public Byte mock(MockConfig config) {
        return (byte) RandomUtils.nextInt(config.getByteRange()[0], config.getByteRange()[1]);
    }

}
