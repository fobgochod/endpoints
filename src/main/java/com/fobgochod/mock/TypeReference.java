package com.fobgochod.mock;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类型引用
 * 用以获取泛型的类型
 *
 * @param <T> 泛型
 */
public abstract class TypeReference<T> {

    private final Type type;

    public TypeReference() {
        Type superClass = this.getClass().getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        } else {
            this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        }
    }

    public Type getType() {
        return type;
    }
}
