package com.x.bp.common.utils;

import java.util.Random;


public class RandomStringGenerator {
    // 扩展字符池，包含大写字母、小写字母和数字
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    // 生成指定长度的随机字符串
    public static String generateRandomCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHAR_POOL.length()); // 随机选择一个字符
            code.append(CHAR_POOL.charAt(index)); // 添加到字符串中
        }
        return code.toString(); // 返回生成的随机字符串
    }
}
