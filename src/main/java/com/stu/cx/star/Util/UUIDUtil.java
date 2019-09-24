package com.stu.cx.star.Util;

import java.util.UUID;

/**
 * @Author: riskychan
 * @Description:create token by uuid
 * @Date: Create in 15:04 2019/9/18
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
