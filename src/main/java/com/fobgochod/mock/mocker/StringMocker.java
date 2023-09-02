package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.enums.StringType;
import com.fobgochod.mock.util.RandomUtils;

import java.util.List;
import java.util.UUID;

/**
 * 模拟String对象
 */
public class StringMocker implements Mocker<String> {

    @Override
    public String mock(MockConfig mockConfig) {
        if (mockConfig.getStringType() == StringType.UUID) {
            return UUID.randomUUID().toString();
        }
        if (mockConfig.getStringType() == StringType.CHARACTER) {
            return mockerCharacter(mockConfig);
        }
        return mockChinese(mockConfig);
    }

    /**
     * 随机截取一段中文返回
     */
    private String mockChinese(MockConfig mockConfig) {
        int index = RandomUtils.nextSize(mockConfig.getSizeRange()[0], mockConfig.getSizeRange()[1]);
        List<String> chineseSeed = mockConfig.getChineseSeed();
        return chineseSeed.get(index);
    }

    /**
     * 生成随机多个字符
     */
    private String mockerCharacter(MockConfig mockConfig) {
        int size = RandomUtils.nextSize(mockConfig.getSizeRange()[0], mockConfig.getSizeRange()[1]);
        String[] stringSeed = mockConfig.getStringSeed();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(stringSeed[RandomUtils.nextInt(0, stringSeed.length)]);
        }
        return sb.toString();
    }
}
