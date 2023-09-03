package com.fobgochod.mock;

import com.fobgochod.mock.enums.StringType;
import com.fobgochod.mock.mocker.*;
import com.fobgochod.mock.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 模拟数据配置类
 */
public class MockConfig {

    private char[] charSeed = StringUtils.CHAR_SEED;
    private String[] stringSeed = StringUtils.STRING_SEED;
    private List<String> chineseSeed = StringUtils.loadChineseSeed();

    private final byte[] byteRange = {0, 127};
    private final short[] shortRange = {0, 1000};
    private final int[] intRange = {0, 10000};
    private final float[] floatRange = {0.0f, 10000.00f};
    private final double[] doubleRange = {0.0, 10000.00};
    private final long[] longRange = {0L, 10000L};
    private final String[] dateRange = {"1970-01-01", "2100-12-31"};
    private final int[] sizeRange = {0, 30};
    private StringType stringType = StringType.CHINESE;
    private boolean enabledCircle = false;

    private static final ByteMocker BYTE_MOCKER = new ByteMocker();
    private static final BooleanMocker BOOLEAN_MOCKER = new BooleanMocker();
    private static final CharacterMocker CHARACTER_MOCKER = new CharacterMocker();
    private static final ShortMocker SHORT_MOCKER = new ShortMocker();
    private static final IntegerMocker INTEGER_MOCKER = new IntegerMocker();
    private static final LongMocker LONG_MOCKER = new LongMocker();
    private static final FloatMocker FLOAT_MOCKER = new FloatMocker();
    private static final DoubleMocker DOUBLE_MOCKER = new DoubleMocker();
    private static final BigIntegerMocker BIG_INTEGER_MOCKER = new BigIntegerMocker();
    private static final BigDecimalMocker BIG_DECIMAL_MOCKER = new BigDecimalMocker();
    private static final StringMocker STRING_MOCKER = new StringMocker();
    private static final DateMocker DATE_MOCKER = new DateMocker();
    private static final LocalTimeMocker LOCAL_TIME_MOCKER = new LocalTimeMocker();
    private static final LocalDateMocker LOCAL_DATE_MOCKER = new LocalDateMocker();
    private static final LocalDateTimeMocker LOCAL_DATE_TIME_MOCKER = new LocalDateTimeMocker();
    private static final TimestampMocker TIMESTAMP_MOCKER = new TimestampMocker();

    private final Map<Class<?>, Mocker<?>> mockerContext = new HashMap<>();
    private final Map<String, Type> typeVariableCache = new HashMap<>();
    private final Map<String, Object> beanCache = new HashMap<>();
    private final Map<String, Enum<?>[]> enumCache = new HashMap<>();


    public MockConfig() {
        register(BYTE_MOCKER, byte.class, Byte.class);
        register(BOOLEAN_MOCKER, boolean.class, Boolean.class);
        register(CHARACTER_MOCKER, char.class, Character.class);
        register(SHORT_MOCKER, short.class, Short.class);
        register(INTEGER_MOCKER, Integer.class, int.class);
        register(LONG_MOCKER, long.class, Long.class);
        register(FLOAT_MOCKER, float.class, Float.class);
        register(DOUBLE_MOCKER, double.class, Double.class);
        register(BIG_INTEGER_MOCKER, BigInteger.class);
        register(BIG_DECIMAL_MOCKER, BigDecimal.class);
        register(STRING_MOCKER, String.class);
        register(DATE_MOCKER, Date.class);
        register(LOCAL_TIME_MOCKER, LocalTime.class);
        register(LOCAL_DATE_MOCKER, LocalDate.class);
        register(LOCAL_DATE_TIME_MOCKER, LocalDateTime.class);
        register(TIMESTAMP_MOCKER, Timestamp.class);
    }

    public MockConfig init(Type type) {
        if (type instanceof ParameterizedType) {
            Class<?> clazz = (Class<?>) ((ParameterizedType) type).getRawType();
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            TypeVariable<?>[] typeVariables = clazz.getTypeParameters();
            IntStream.range(0, typeVariables.length)
                    .forEach(index -> typeVariableCache.put(typeVariables[index].getName(), types[index]));
        }
        return this;
    }

    private void register(Mocker<?> mocker, Class<?>... classes) {
        for (Class<?> clazz : classes) {
            mockerContext.put(clazz, mocker);
        }
    }

    public Mocker<?> getMocker(Class<?> clazz) {
        return mockerContext.get(clazz);
    }

    public Type getVariableType(String name) {
        return typeVariableCache.get(name);
    }

    public void cacheBean(String name, Object bean) {
        beanCache.put(name, bean);
    }

    public Object getCacheBean(String beanClassName) {
        return beanCache.get(beanClassName);
    }

    public void cacheEnum(String name, Enum<?>[] enums) {
        enumCache.put(name, enums);
    }

    public Enum<?>[] getCacheEnum(String enumClassName) {
        return enumCache.get(enumClassName);
    }

    public MockConfig charSeed(char... charSeed) {
        this.charSeed = charSeed;
        return this;
    }

    public MockConfig stringSeed(String... stringSeed) {
        this.stringSeed = stringSeed;
        return this;
    }

    public void chineseSeed(List<String> chineseSeed) {
        this.chineseSeed = chineseSeed;
    }

    public MockConfig byteRange(byte min, byte max) {
        this.byteRange[0] = min;
        this.byteRange[1] = max;
        return this;
    }

    public MockConfig shortRange(short min, short max) {
        this.shortRange[0] = min;
        this.shortRange[1] = max;
        return this;
    }

    public MockConfig intRange(int min, int max) {
        this.intRange[0] = min;
        this.intRange[1] = max;
        return this;
    }

    public MockConfig floatRange(float min, float max) {
        this.floatRange[0] = min;
        this.floatRange[1] = max;
        return this;
    }

    public MockConfig doubleRange(double min, double max) {
        this.doubleRange[0] = min;
        this.doubleRange[1] = max;
        return this;
    }

    public MockConfig longRange(long min, long max) {
        this.longRange[0] = min;
        this.longRange[1] = max;
        return this;
    }

    public MockConfig dateRange(String min, String max) {
        this.dateRange[0] = min;
        this.dateRange[1] = max;
        return this;
    }

    public MockConfig sizeRange(int min, int max) {
        this.sizeRange[0] = min;
        this.sizeRange[1] = max;
        return this;
    }

    public char[] getCharSeed() {
        return charSeed;
    }

    public String[] getStringSeed() {
        return stringSeed;
    }

    public List<String> getChineseSeed() {
        return chineseSeed;
    }

    public byte[] getByteRange() {
        return byteRange;
    }

    public short[] getShortRange() {
        return shortRange;
    }

    public int[] getIntRange() {
        return intRange;
    }

    public float[] getFloatRange() {
        return floatRange;
    }

    public double[] getDoubleRange() {
        return doubleRange;
    }

    public long[] getLongRange() {
        return longRange;
    }

    public String[] getDateRange() {
        return dateRange;
    }

    public int[] getSizeRange() {
        return sizeRange;
    }

    public StringType getStringType() {
        return stringType;
    }

    public MockConfig setStringType(StringType stringType) {
        this.stringType = stringType;
        return this;
    }

    public boolean isEnabledCircle() {
        return enabledCircle;
    }

    public MockConfig setEnabledCircle(boolean enabledCircle) {
        this.enabledCircle = enabledCircle;
        return this;
    }
}
