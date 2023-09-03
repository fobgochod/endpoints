package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class ClassMocker implements Mocker<Object> {

    private final Class<?> clazz;

    private final Type[] genericTypes;

    ClassMocker(Class<?> clazz, Type[] genericTypes) {
        this.clazz = clazz;
        this.genericTypes = genericTypes;
    }

    @Override
    public Object mock(MockConfig config) {
        Mocker<?> mocker;
        if (clazz.isArray()) {
            mocker = new ArrayMocker(clazz);
        } else if (Map.class.isAssignableFrom(clazz)) {
            mocker = new MapMocker(genericTypes);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            mocker = new CollectionMocker(clazz, genericTypes[0]);
        } else if (clazz.isEnum()) {
            mocker = new EnumMocker<>(clazz);
        } else {
            mocker = config.getMocker(clazz);
            if (mocker == null) {
                mocker = new BeanMocker(clazz);
            }
        }
        return mocker.mock(config);
    }

}
