package com.fobgochod.mock.enums;

public class PrimitiveBean {

    private static final PrimitiveBean INSTANCE = new PrimitiveBean();
    private byte aByte;
    private boolean aBoolean;
    private char aChar;
    private short aShort;
    private int anInt;
    private long aLong;
    private float aFloat;
    private double aDouble;

    public static Object of(String clazz) {
        BaseType baseType = BaseType.get(clazz);
        return switch (baseType) {
            case byte_ -> INSTANCE.aByte;
            case boolean_ -> INSTANCE.aBoolean;
            case char_ -> INSTANCE.aChar;
            case short_ -> INSTANCE.aShort;
            case int_ -> INSTANCE.anInt;
            case long_ -> INSTANCE.aLong;
            case float_ -> INSTANCE.aFloat;
            case double_ -> INSTANCE.aDouble;
            default -> null;
        };
    }
}
