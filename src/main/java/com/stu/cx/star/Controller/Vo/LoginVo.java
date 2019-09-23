package com.stu.cx.star.Controller.Vo;


import javax.validation.constraints.NotBlank;

/**
 * @Author: riskychan
 * @Description:登录的校验
 * @Date: Create in 16:24 2019/9/18
 */
public class LoginVo {
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    @NotBlank(message ="密码不能为空")
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
