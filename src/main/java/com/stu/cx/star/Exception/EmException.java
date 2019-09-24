package com.stu.cx.star.Exception;

/**
 * @Author: riskychan
 * @Description:some exception
 * @Date: Create in 16:37 2019/9/18
 */
public enum EmException implements  CommonException {
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOW_ERROR(10002,"未知错误"),
    USER_NOT_EXIST(20001,"用户不存在"),
    EMAIL_ISEXIST(20002,"该邮箱已经被注册"),
    IS_NOT_AGREE(20003,"你还未阅读勾选协议"),
    PASSWORD_ERROR(20004,"密码错误"),
    MAIL_EMPTY(20005,"邮箱为空"),
    USER_ISEXIST(20006,"该手机号已经被注册"),
    SEND_MAIL_FAILED(20007,"发送邮件失败"),
    MAIL_IS_NOTEXIST(20008,"邮箱不存在"),
    OTP_OUT_TIME(30001,"验证码失效，请重新注册"),
    OTP_ERROR(30002,"验证码错误，请填写正确验证码"),
    ;


    public int errCode;
    public  String errMsg;

    EmException(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public CommonException setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return null;
    }
}
