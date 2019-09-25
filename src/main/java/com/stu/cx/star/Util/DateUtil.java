package com.stu.cx.star.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: riskychan
 * @Description:get current time
 * @Date: Create in 10:30 2019/9/25
 */
public class DateUtil {
    public static String getCurrentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
