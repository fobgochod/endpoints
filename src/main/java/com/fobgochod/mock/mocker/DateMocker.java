package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.util.RandomUtils;

import java.util.Date;

/**
 * Date对象模拟器
 */
public class DateMocker extends AbstractDateMock<Date> {

    @Override
    public Date mock(MockConfig config) {
        super.mock(config);
        return new Date(RandomUtils.nextLong(startTime, endTime));
    }
}
