package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

@SuppressWarnings("unchecked")
public class BaseMocker<T> implements Mocker<T> {

    private final Type type;
    private final Type[] genericTypes;

    public BaseMocker(Type type, Type... genericTypes) {
        this.type = type;
        this.genericTypes = genericTypes;
    }

    @Override
    public T mock(MockConfig config) {
        Mocker<?> mocker;
        if (type instanceof ParameterizedType parameterizedType) {
            mocker = new GenericMocker(parameterizedType);
        } else if (type instanceof GenericArrayType genericArrayType) {
            mocker = new ArrayMocker(genericArrayType);
        } else if (type instanceof TypeVariable<?> typeVariable) {
            mocker = new BaseMocker<>(config.getVariableType(typeVariable.getName()));
        } else {
            mocker = new ClassMocker((Class<?>) type, genericTypes);
        }
        return (T) mocker.mock(config);
    }

}
