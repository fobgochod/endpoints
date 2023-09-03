package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.util.RandomUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateMocker extends AbstractDateMock<LocalDate> {


    @Override
    public LocalDate mock(MockConfig config) {
        super.mock(config);
        long start = LocalDate.ofInstant(new Date(startTime).toInstant(), ZoneId.systemDefault()).toEpochDay();
        long end = LocalDate.ofInstant(new Date(endTime).toInstant(), ZoneId.systemDefault()).toEpochDay();
        return LocalDate.ofEpochDay(RandomUtils.nextLong(start, end));
    }
}
