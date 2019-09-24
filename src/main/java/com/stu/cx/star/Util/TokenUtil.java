package com.stu.cx.star.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.stu.cx.star.Entity.Login;

/**
 * @Author: riskychan
 * @Description:use the jwt to create token
 * @Date: Create in 14:13 2019/9/24
 */
public class TokenUtil {

    public static String getToken(Login login){
        String token = "";
        token = JWT.create().sign(Algorithm.HMAC256(login.getPassword()));
        return token;
    }
}
