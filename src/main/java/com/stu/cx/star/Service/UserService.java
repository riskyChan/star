package com.stu.cx.star.Service;

import com.stu.cx.star.Controller.Vo.ForgetPasswordVo;
import com.stu.cx.star.Controller.Vo.LoginVo;
import com.stu.cx.star.Controller.Vo.RegisterVo;
import com.stu.cx.star.Exception.UserException;
import org.springframework.stereotype.Service;

/**
 * @Author: riskychan
 * @Description:用户注册登录等相关抽象类
 * @Date: Create in 16:09 2019/9/18
 */
public interface UserService {

    //登录
    public void login(LoginVo loginVo) throws UserException;

    //获取邮箱验证码
    public void getMailOtp(String mail) throws UserException;

    //注册
    public void register(RegisterVo registerVo) throws UserException;

    //获取找回密码的验证码
    public void getForgetOtp(String mail) throws UserException;

    //重置密码
    public void forgetPass(ForgetPasswordVo forgetPasswordVo) throws UserException;

}
