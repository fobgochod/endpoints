package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.util.RandomUtils;

import java.sql.Timestamp;
import java.util.Date;

public class TimestampMocker extends AbstractDateMock<Timestamp> {

    @Override
    public Timestamp mock(MockConfig mockConfig) {
        super.mock(mockConfig);
        Date date = new Date(RandomUtils.nextLong(startTime, endTime));
        return Timestamp.from(date.toInstant());
    }
}
