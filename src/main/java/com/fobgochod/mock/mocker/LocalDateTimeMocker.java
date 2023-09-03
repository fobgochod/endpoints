package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.util.RandomUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeMocker extends AbstractDateMock<LocalDateTime> {

    @Override
    public LocalDateTime mock(MockConfig config) {
        super.mock(config);
        Date date = new Date(RandomUtils.nextLong(startTime, endTime));
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
