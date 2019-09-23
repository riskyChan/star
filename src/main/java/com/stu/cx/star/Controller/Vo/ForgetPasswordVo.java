package com.stu.cx.star.Controller.Vo;

import javax.validation.constraints.NotBlank;

/**
 * @Author: riskychan
 * @Description:忘记密码重置操作
 * @Date: Create in 16:31 2019/9/20
 */
public class ForgetPasswordVo {
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "验证码不能为空")
    private String otp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
