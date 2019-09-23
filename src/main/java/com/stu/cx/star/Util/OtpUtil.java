package com.stu.cx.star.Util;

import java.util.Random;

/**
 * @Author: riskychan
 * @Description:自动生成验证码
 * @Date: Create in 9:53 2019/9/19
 */
public class OtpUtil {
    public static String getMailOtp(){
        String val = "";
        Random random = new Random();
        for(int i = 0 ;i < 6 ; i++){
            //数字或字母
            String charOrNum = random.nextInt(2)%2 == 0?"char":"num";
            if("char".equalsIgnoreCase(charOrNum)){
                //大小写
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            }else{
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
