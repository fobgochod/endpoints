package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

import java.math.BigInteger;

/**
 * BigInteger对象模拟器
 */
public class BigIntegerMocker implements Mocker<BigInteger> {

    @Override
    public BigInteger mock(MockConfig config) {
        return BigInteger.valueOf(RandomUtils.nextLong(config.getLongRange()[0], config.getLongRange()[1]));
    }

}
