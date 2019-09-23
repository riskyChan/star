package com.stu.cx.star.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: riskychan
 * @Description:用户登录，注册，找回密码出现的异常处理
 * @Date: Create in 16:39 2019/9/18
 */
public class UserException extends Exception implements CommonException  {

    private CommonException commonException;

    //枚举的
    public UserException(CommonException commonException){
        super();
        this.commonException = commonException;
    }

    //自定义
    public UserException (CommonException commonException,String msg){
        super();
        this.commonException = commonException;
        this.setErrMsg(msg);
    }

    @Override
    public int getErrCode() {
        return this.commonException.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonException.getErrMsg();
    }

    @Override
    public CommonException setErrMsg(String errMsg) {
        this.commonException.setErrMsg(errMsg);
        return this;
    }
}
