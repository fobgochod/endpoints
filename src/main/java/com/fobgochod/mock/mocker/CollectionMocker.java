package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * 模拟Collection
 */
public class CollectionMocker implements Mocker<Object> {

    private final Class<?> clazz;

    private final Type genericType;

    CollectionMocker(Class<?> clazz, Type genericType) {
        this.clazz = clazz;
        this.genericType = genericType;
    }

    @Override
    public Object mock(MockConfig mockConfig) {
        int size = RandomUtils.nextSize(mockConfig.getSizeRange()[0], mockConfig.getSizeRange()[1]);
        Collection<Object> result;
        if (List.class.isAssignableFrom(clazz)) {
            result = new ArrayList<>(size);
        } else {
            result = new HashSet<>(size);
        }
        BaseMocker baseMocker = new BaseMocker(genericType);
        for (int index = 0; index < size; index++) {
            result.add(baseMocker.mock(mockConfig));
        }
        return result;
    }

}
