package com.stu.cx.star.Controller.Vo;

import javax.validation.constraints.NotBlank;

/**
 * @Author: riskychan
 * @Description:forget password ,reset password
 * @Date: Create in 16:31 2019/9/20
 */
public class ForgetPasswordVo {
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "验证码不能为空")
    private String otp;
    @NotBlank(message = "新密码不能为空")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
