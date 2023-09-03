package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;

import java.lang.reflect.ParameterizedType;

/**
 * 模拟泛型
 */
public class GenericMocker implements Mocker<Object> {

    private final ParameterizedType type;

    GenericMocker(ParameterizedType type) {
        this.type = type;
    }

    @Override
    public Object mock(MockConfig config) {
        return new BaseMocker<>(type.getRawType(), type.getActualTypeArguments()).mock(config);
    }

}
