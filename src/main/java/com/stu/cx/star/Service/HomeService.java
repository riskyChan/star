package com.stu.cx.star.Service;

import com.stu.cx.star.Controller.Vo.LoginLogVo;
import com.stu.cx.star.Exception.UserException;

import java.util.List;

/**
 * @Author: riskychan
 * @Description:operate on home
 * @Date: Create in 14:44 2019/9/25
 */
public interface HomeService {

    public List<LoginLogVo> getLoginLog(String mobile,String startTime,String endTime) throws UserException;
}
