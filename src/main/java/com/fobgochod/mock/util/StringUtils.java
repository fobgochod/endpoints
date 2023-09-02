package com.fobgochod.mock.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串工具
 *
 * @author fobgochod
 */
public final class StringUtils {

    private static final String DEFAULT_DIC_PATH = "library/default.dic";

    public static final char[] CHAR_SEED = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static final String[] STRING_SEED = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public static List<String> loadChineseSeed() {
        try (InputStream is = StringUtils.class.getClassLoader().getResourceAsStream(DEFAULT_DIC_PATH)) {
            if (is == null) return Collections.emptyList();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Could not load '" + DEFAULT_DIC_PATH + "': " + e.getMessage());
        }
    }
}
