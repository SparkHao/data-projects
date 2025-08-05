package com.depinx.data.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author
 */
public class RandomUtils {
    public static final String SIMPLE_CHAR = "0123456789";
    public static final String ALL_CHAR = "0123456789abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^`~&*()/;:";
    public static final String ALL_NUM_AND_CHAR_CHAR = "0123456789abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateSimpleString(int length) {
        return generateString(length, SIMPLE_CHAR);
    }
    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateAllCharString(int length) {
        return generateString(length, ALL_CHAR);
    }
    /**
     * 返回一个定长的随机字符串
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    private static String generateString(int length,String allChar) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    public static String generateOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date()); // 获取当前时间
        int randomNum = new Random().nextInt(900000) + 100000; // 6位随机数
        return timestamp + randomNum; // 时间戳 + 随机数
    }


    public static void main(String[] args) {
        String x = generateSimpleString(6);
        System.out.println(x);
        String s = generateAllCharString(64);
        System.out.println(s);
        long l = System.currentTimeMillis();
        System.out.println(l);
        System.out.println(generateOrderNumber());

    }

    public static Double generateApy() {
        int min = 200;
        int max = 300;

        return (Math.random() * (max - min + 1)) + min;
    }

    public static String generateCode(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(ALL_NUM_AND_CHAR_CHAR.charAt(random.nextInt(ALL_NUM_AND_CHAR_CHAR.length())));
        }
        return code.toString();
    }
}
