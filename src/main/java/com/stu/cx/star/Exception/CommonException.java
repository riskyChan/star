package com.stu.cx.star.Exception;

/**
 * @Author: riskychan
 * @Description:统一的异常接口
 * @Date: Create in 16:35 2019/9/18
 */

public interface CommonException {
    public int getErrCode();
    public String getErrMsg();
    public CommonException setErrMsg(String errMsg);
}
