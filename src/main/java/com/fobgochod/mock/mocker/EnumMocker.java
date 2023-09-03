package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.MockException;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

import java.lang.reflect.Field;

/**
 * Double对象模拟器
 */
public class EnumMocker<T extends Enum<T>> implements Mocker<T> {

    private final Class<?> clazz;

    public EnumMocker(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mock(MockConfig config) {
        Enum<?>[] enums = config.getCacheEnum(clazz.getName());
        if (enums == null) {
            try {
                Field field = clazz.getDeclaredField("$VALUES");
                field.setAccessible(true);
                enums = (Enum<?>[]) field.get(clazz);
                if (enums.length == 0) {
                    throw new MockException("空的enum不能模拟");
                }
                config.cacheEnum(clazz.getName(), enums);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new MockException(e);
            }
        }
        return (T) enums[RandomUtils.nextInt(0, enums.length)];
    }

}
