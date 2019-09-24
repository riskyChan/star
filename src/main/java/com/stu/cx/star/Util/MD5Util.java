package com.stu.cx.star.Util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: riskychan
 * @Description:md5 encrpt
 * @Date: Create in 15:06 2019/9/18
 */
public class MD5Util {
    //for test convince , write  salt
    private static final String salt = "1a2b3c4d5f";

    //encrpt
    public static String encrpt(String password){
        String newPass = DigestUtils.md5Hex(password+salt);
        return newPass;
    }
}
