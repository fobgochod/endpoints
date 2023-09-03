package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

import java.math.BigDecimal;

/**
 * BigDecimal对象模拟器
 */
public class BigDecimalMocker implements Mocker<BigDecimal> {

    @Override
    public BigDecimal mock(MockConfig config) {
        return BigDecimal.valueOf(RandomUtils.nextDouble(config.getDoubleRange()[0], config.getDoubleRange()[1]));
    }

}
