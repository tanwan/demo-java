/*
 * Created by LZY on 2017-06-12.
 */
package com.lzy.demo.common.util;

import java.util.regex.Pattern;


/**
 * 校验工具类
 *
 * @author LZY
 * @version v1.0
 */
public class CheckUtils {

    private static final String EMAIL_PATTERN = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    private static final String IDCARD_PATTERN = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
    private static final String PHONE_PATTERN = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
    private static final String IPV4_PATTERN = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
    private static final String URL_PATTERN = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
    private static final String CHINESE_PATTERN = "^[\\u4E00-\\u9FA5]+$";
    private static final String INTEGER_PATTERN = "^(-?[1-9]\\d*|0)$";
    private static final String NUMERIC_PATTERN = "^-?\\d+\\.?\\d*$";

    /**
     * 校验正则,字符串为空返回false
     *
     * @param content the content
     * @param pattern the pattern
     * @return boolean
     */
    public static boolean checkRegex(String content, String pattern) {
        return content != null && pattern != null && Pattern.matches(pattern, content);
    }

    /**
     * 校验邮箱
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean checkEmail(String email) {
        return checkRegex(email, EMAIL_PATTERN);
    }


    /**
     * 校验身份证
     *
     * @param idCard the id card
     * @return the boolean
     */
    public static boolean checkIdCard(String idCard) {
        return checkRegex(idCard, IDCARD_PATTERN);
    }

    /**
     * 校验国内电话号码
     *
     * @param phone the phone
     * @return the boolean
     */
    public static boolean checkPhone(String phone) {
        return checkRegex(phone, PHONE_PATTERN);
    }

    /**
     * 校验整数
     *
     * @param number the number
     * @return the boolean
     */
    public static boolean checkInteger(String number) {
        return checkRegex(number, INTEGER_PATTERN);
    }

    /**
     * 校验整数和浮点数
     *
     * @param numeric the numeric
     * @return the boolean
     */
    public static boolean checkNumeric(String numeric) {
        return checkRegex(numeric, NUMERIC_PATTERN);
    }


    /**
     * 校验中文
     *
     * @param chinese the chinese
     * @return the boolean
     */
    public static boolean checkChinese(String chinese) {
        return checkRegex(chinese, CHINESE_PATTERN);
    }

    /**
     * 校验URL
     *
     * @param url the url
     * @return the boolean
     */
    public static boolean checkURL(String url) {
        return checkRegex(url, URL_PATTERN);
    }

    /**
     * 校验IPV4
     *
     * @param ipAddress the ip address
     * @return the boolean
     */
    public static boolean checkIPV4(String ipAddress) {
        return checkRegex(ipAddress, IPV4_PATTERN);
    }
}
