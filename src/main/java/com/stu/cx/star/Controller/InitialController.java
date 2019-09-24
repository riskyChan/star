package com.stu.cx.star.Controller;

import com.stu.cx.star.Controller.Vo.ForgetPasswordVo;
import com.stu.cx.star.Controller.Vo.LoginVo;
import com.stu.cx.star.Controller.Vo.RegisterVo;
import com.stu.cx.star.Exception.UserException;
import com.stu.cx.star.Response.CommonReturnType;
import com.stu.cx.star.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: riskychan
 * @Description:登录，注册，找回密码等相关的控制器
 * @Date: Create in 16:01 2019/9/18
 */
@RestController
@CrossOrigin(allowCredentials = "true" ,allowedHeaders = "*")
public class InitialController extends BaseController {
    Logger logger = LoggerFactory.getLogger(InitialController.class.getName());

    @Autowired
    private UserService userService;

    //登录
    @RequestMapping("login")
    public CommonReturnType login(@RequestParam(value = "mobile")String mobile,
                                  @RequestParam(value = "password")String password) throws UserException {
        LoginVo loginVo = new LoginVo();
        loginVo.setMobile(mobile);
        loginVo.setPassword(password);
        userService.login(loginVo);
        return CommonReturnType.create(null);
    }

    //获取登录验证码
    @RequestMapping("getMailOtp")
    public CommonReturnType getMailOtp(@RequestParam(value = "email") String email) throws UserException {
        userService.getMailOtp(email);
        return CommonReturnType.create(null,"发送成功，请查看邮箱");
    }

    //注册
    @RequestMapping(value = "register",method = {RequestMethod.POST})
    public CommonReturnType register(@RequestBody RegisterVo registerVo) throws UserException {
        userService.register(registerVo);
        return CommonReturnType.create(null);
    }

    //获取找回密码的验证码
    @RequestMapping(value = "getPassOtp")
    @Transactional(rollbackFor = Exception.class)
    public CommonReturnType getPassOtp(@RequestParam(value = "email")String email) throws UserException {
        userService.getForgetOtp(email);
        return CommonReturnType.create(null);
    }

    //充值密码
    @PostMapping("forgetPass")
    @Transactional(rollbackFor = Exception.class)
    public CommonReturnType forgetPass(@RequestBody ForgetPasswordVo passwordVo) throws UserException {
        userService.forgetPass(passwordVo);
        return CommonReturnType.create(null);
    }
}
