package com.stu.cx.star.Util;

import java.util.Random;

/**
 * @Author: riskychan
 * @Description:create otp
 * @Date: Create in 9:53 2019/9/19
 */
public class OtpUtil {
    public static String getMailOtp(){
        String val = "";
        Random random = new Random();
        for(int i = 0 ;i < 6 ; i++){
            //num or char?
            String charOrNum = random.nextInt(2)%2 == 0?"char":"num";
            if("char".equalsIgnoreCase(charOrNum)){
                //upper or low?
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            }else{
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
