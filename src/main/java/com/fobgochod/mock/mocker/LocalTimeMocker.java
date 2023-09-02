package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.util.RandomUtils;

import java.time.LocalTime;

public class LocalTimeMocker extends AbstractDateMock<LocalTime> {

    @Override
    public LocalTime mock(MockConfig mockConfig) {
        super.mock(mockConfig);
        return LocalTime.ofNanoOfDay(RandomUtils.nextLong(startTime, endTime));
    }
}
