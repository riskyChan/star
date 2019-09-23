package com.stu.cx.star.Util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: riskychan
 * @Description:用户密码进行md5加密
 * @Date: Create in 15:06 2019/9/18
 */
public class MD5Util {
    //测试方便，把盐写死
    private static final String salt = "1a2b3c4d5f";

    //加密
    public static String encrpt(String password){
        String newPass = DigestUtils.md5Hex(password+salt);
        return newPass;
    }
}
