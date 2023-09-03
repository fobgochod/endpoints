package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.enums.StringType;
import com.fobgochod.mock.util.RandomUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 模拟String对象
 */
public class StringMocker implements Mocker<String> {

    @Override
    public String mock(MockConfig config) {
        if (config.getStringType() == StringType.UUID) {
            return UUID.randomUUID().toString();
        }
        if (config.getStringType() == StringType.CHARACTER) {
            return mockerCharacter(config);
        }
        return mockChinese(config);
    }

    /**
     * 随机截取一段中文返回
     */
    private String mockChinese(MockConfig config) {
        int index = RandomUtils.nextSize(config.getSizeRange()[0], config.getSizeRange()[1]);
        List<String> chineseSeed = config.getChineseSeed();
        return chineseSeed.get(index);
    }

    /**
     * 生成随机多个字符
     */
    private String mockerCharacter(MockConfig config) {
        int size = RandomUtils.nextSize(config.getSizeRange()[0], config.getSizeRange()[1]);
        String[] stringSeed = config.getStringSeed();
        return IntStream.range(0, size).mapToObj(i -> stringSeed[RandomUtils.nextInt(0, stringSeed.length)]).collect(Collectors.joining());
    }
}
