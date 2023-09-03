package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 模拟Map
 */
public class MapMocker implements Mocker<Object> {

    private final Type[] types;

    MapMocker(Type[] types) {
        this.types = types;
    }

    @Override
    public Object mock(MockConfig config) {
        int size = RandomUtils.nextSize(config.getSizeRange()[0], config.getSizeRange()[1]);
        BaseMocker<?> keyMocker = new BaseMocker<>(types[0]);
        BaseMocker<?> valueMocker = new BaseMocker<>(types[1]);
        return IntStream.range(0, size).boxed().collect(Collectors.toMap(index -> keyMocker.mock(config), index -> valueMocker.mock(config), (a, b) -> b, () -> new HashMap<>(size)));
    }

}
