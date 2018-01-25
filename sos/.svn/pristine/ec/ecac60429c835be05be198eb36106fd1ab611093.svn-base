package com.gboss.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
    private static Random random = null;

    /**
     * 生成1-32为UUID
     * @param length 1~32
     */
    public static String createString(int length) {

        String randomStr = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        if (length < 32 && length > 0)
            randomStr = randomStr.substring(0, length);
        return randomStr;
    }

    /**
     * 生成长度为1-32为长度的随机数字
     * @param length 1~32
     */
    public static String createNumber(int length) {

        if (length <= 0)
            length = 1;

        if (random == null)
            random = new Random();

        StringBuffer bf = new StringBuffer();

        bf.append(random.nextInt(99999999));
        bf.append(random.nextInt(99999999));
        bf.append(random.nextInt(99999999));
        bf.append(random.nextInt(99999999));

        if (length > 32)
            length = 32;

        if (bf.length() < length)
            bf.append(random.nextInt(99999999));
        if (bf.length() < length)
            bf.append(random.nextInt(99999999));

        return bf.toString().substring(0, length);
    }
}
