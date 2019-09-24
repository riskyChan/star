package com.stu.cx.star.Service;

import com.stu.cx.star.Controller.Vo.ForgetPasswordVo;
import com.stu.cx.star.Controller.Vo.LoginVo;
import com.stu.cx.star.Controller.Vo.RegisterVo;
import com.stu.cx.star.Exception.UserException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: riskychan
 * @Description:interface for login ,resgister,forget password
 * @Date: Create in 16:09 2019/9/18
 */
public interface UserService {

    //login
    public void login(HttpServletResponse response, LoginVo loginVo) throws UserException;

    //register otp
    public void getMailOtp(String mail) throws UserException;

    //register
    public void register(RegisterVo registerVo) throws UserException;

    //forget password top
    public void getForgetOtp(String mail) throws UserException;

    //reset password
    public void forgetPass(ForgetPasswordVo forgetPasswordVo) throws UserException;

}
