package com.lzy.demo.base.utils;


import java.security.MessageDigest;
import java.util.Base64;

public class EncodeUtils {
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String MD5_ALGORITHM = "md5";

    /**
     * md5编码
     *
     * @param text text
     * @return the string
     */
    public static String md5Encode(String text) {
        return md5Encode(text, false);
    }

    /**
     * md5编码
     *
     * @param text           text
     * @param encodeAsBase64 是否使用base64编码
     * @return the string
     */
    public static String md5Encode(String text, boolean encodeAsBase64) {
        try {
            byte[] btInput = text.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance(MD5_ALGORITHM);
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            if (encodeAsBase64) {
                return Base64.getEncoder().encodeToString(md);
            } else {
                return new String(hexEncode(md));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * base64编码
     *
     * @param binaryData 原始字节码
     * @return base64后的字符串 string
     */
    public static String base64Encode(byte[] binaryData) {
        return Base64.getEncoder().encodeToString(binaryData);
    }

    /**
     * base64解码
     *
     * @param base64String base64后的字符串
     * @return 原始字节码 byte [ ]
     */
    public static byte[] base64Decode(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    /**
     * 将bytes数组转化成16进制表示的char[]
     *
     * @param bytes the bytes
     * @return the char [ ]
     */
    public static char[] hexEncode(byte[] bytes) {
        final int nBytes = bytes.length;
        char[] result = new char[2 * nBytes];

        int j = 0;
        for (int i = 0; i < nBytes; i++) {
            // Char for top 4 bits
            result[j++] = HEX[(0xF0 & bytes[i]) >>> 4];
            // Bottom 4
            result[j++] = HEX[(0x0F & bytes[i])];
        }

        return result;
    }

    /**
     * hexEncode的逆过程
     *
     * @param s the s
     * @return the byte [ ]
     * @throws IllegalArgumentException maybe throw IllegalArgumentException
     */
    public static byte[] hexDecode(CharSequence s) {
        int nChars = s.length();

        if (nChars % 2 != 0) {
            throw new IllegalArgumentException(
                    "Hex-encoded string must have an even number of characters");
        }

        byte[] result = new byte[nChars / 2];

        for (int i = 0; i < nChars; i += 2) {
            int msb = Character.digit(s.charAt(i), 16);
            int lsb = Character.digit(s.charAt(i + 1), 16);

            if (msb < 0 || lsb < 0) {
                throw new IllegalArgumentException("Non-hex character in input: " + s);
            }
            result[i / 2] = (byte) ((msb << 4) | lsb);
        }
        return result;
    }
}
