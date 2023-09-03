package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.MockException;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.serializer.Constants;

import java.text.ParseException;

public abstract class AbstractDateMock<T> implements Mocker<T> {

    protected Long startTime;
    protected Long endTime;

    @Override
    public T mock(MockConfig config) {
        try {
            this.startTime = Constants.SIMPLE_DATE_FORMAT.parse(config.getDateRange()[0]).getTime();
            this.endTime = Constants.SIMPLE_DATE_FORMAT.parse(config.getDateRange()[1]).getTime();
        } catch (ParseException e) {
            throw new MockException("时间格式设置错误，设置如下格式yyyy-MM-dd ", e);
        }
        return null;
    }
}
