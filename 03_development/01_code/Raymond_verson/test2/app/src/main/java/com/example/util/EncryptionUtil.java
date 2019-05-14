package com.example.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
    public static String string2MD5(String sourceStr) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] b = sourceStr.getBytes();
            byte[] digest = md5.digest(b);
            char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7' , '8', '9', 'A', 'B', 'C', 'D', 'E','F' };
            for (byte bb : digest) {
                sb.append(chars[(bb >> 4) & 0xf]);sb.append(chars[bb & 0xf]);
            }
            System.out.println(sb);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
