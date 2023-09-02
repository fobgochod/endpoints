package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.MockException;
import com.fobgochod.mock.Mocker;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class AbstractDateMock<T> implements Mocker<T> {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    protected Long startTime;
    protected Long endTime;

    @Override
    public T mock(MockConfig mockConfig) {
        try {
            this.startTime = FORMAT.parse(mockConfig.getDateRange()[0]).getTime();
            this.endTime = FORMAT.parse(mockConfig.getDateRange()[1]).getTime();
        } catch (ParseException e) {
            throw new MockException("时间格式设置错误，设置如下格式yyyy-MM-dd ", e);
        }
        return null;
    }
}
