package com.stu.cx.star.Controller.Vo;

/**
 * @Author: riskychan
 * @Description:show in the page
 * @Date: Create in 15:39 2019/9/25
 */
public class LoginLogVo {
    private Integer key;
    private String mobile;
    private String loginTime;
    private String loginIp;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
