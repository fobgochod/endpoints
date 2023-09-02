package com.fobgochod.mock.mocker;

import com.fobgochod.mock.MockConfig;
import com.fobgochod.mock.Mocker;
import com.fobgochod.mock.util.RandomUtils;

/**
 * Character对象模拟器
 */
public class CharacterMocker implements Mocker<Character> {

    @Override
    public Character mock(MockConfig mockConfig) {
        char[] charSeed = mockConfig.getCharSeed();
        return charSeed[RandomUtils.nextInt(0, charSeed.length)];
    }

}
