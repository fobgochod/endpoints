package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.IntStream;

/**
 * 数组模拟器
 */
public class ArrayMocker implements Mocker<Object> {

    private final Type type;

    ArrayMocker(Type type) {
        this.type = type;
    }

    @Override
    public Object mock(MockConfig config) {
        // 创建有参数化的数组
        if (type instanceof GenericArrayType) {
            return createGenericArray(config);
        }
        return array(config);
    }

    private Object array(MockConfig config) {
        int size = RandomUtils.nextSize(config.getSizeRange()[0], config.getSizeRange()[1]);
        Class<?> componentClass = ((Class<?>) type).getComponentType();
        Object result = Array.newInstance(componentClass, size);
        BaseMocker<?> baseMocker = new BaseMocker<>(componentClass);
        IntStream.range(0, size).forEach(index -> Array.set(result, index, baseMocker.mock(config)));
        return result;
    }

    // TODO 代码还需要整理
    // 由于GenericArrayType无法获得Class，所以递归创建多维数组
    private Object createGenericArray(MockConfig config) {
        GenericArrayType genericArrayType = (GenericArrayType) this.type;
        // 递归获取该数组的维数，以及最后的Class类型
        Map<Integer, Map<Class<?>, Type[]>> map = map(config, genericArrayType, 0);
        Entry<Integer, Map<Class<?>, Type[]>> entry = map.entrySet().iterator().next();
        Entry<Class<?>, Type[]> baseEntry = entry.getValue().entrySet().iterator().next();
        int[] dimensions = new int[entry.getKey()];
        Arrays.setAll(dimensions, index -> RandomUtils.nextSize(config.getSizeRange()[0], config.getSizeRange()[1]));
        // 创建多维数组每种维度的对象
        List<Object> list = new ArrayList<>(dimensions.length);
        Class<?> clazz = baseEntry.getKey();
        for (int index = dimensions.length - 1; index >= 0; index--) {
            Object array = Array.newInstance(clazz, dimensions[index]);
            list.add(array);
            clazz = array.getClass();
        }
        // 实例化多维数组
        Object baseResult = new BaseMocker<>(baseEntry.getKey(), baseEntry.getValue()).mock(config);
        for (int i = 0; i < list.size(); i++) {
            Object array = list.get(i);
            for (int j = 0; j < dimensions[dimensions.length - i - 1]; j++) {
                Array.set(array, j, baseResult);
            }
            baseResult = array;
        }
        return baseResult;
    }

    private Map<Integer, Map<Class<?>, Type[]>> map(MockConfig config, GenericArrayType genericArrayType, int dimension) {
        Map<Integer, Map<Class<?>, Type[]>> result = new HashMap<>();
        Type componentType = genericArrayType.getGenericComponentType();
        dimension++;
        if (componentType instanceof ParameterizedType parameterizedType) {
            Map<Class<?>, Type[]> map = new HashMap<>();
            map.put((Class<?>) parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
            result.put(dimension, map);
            return result;
        }
        if (componentType instanceof GenericArrayType) {
            return map(config, (GenericArrayType) componentType, dimension);
        }
        if (componentType instanceof TypeVariable<?> typeVariable) {
            Map<Class<?>, Type[]> map = new HashMap<>();
            map.put((Class<?>) config.getVariableType(typeVariable.getName()), null);
            result.put(dimension, map);
            return result;
        }
        Map<Class<?>, Type[]> map = new HashMap<>();
        map.put((Class<?>) componentType, null);
        result.put(dimension, map);
        return result;
    }
}
