package com.stu.cx.star.Service.Impl;

import com.stu.cx.star.Controller.Vo.ForgetPasswordVo;
import com.stu.cx.star.Controller.Vo.RegisterVo;
import com.stu.cx.star.Redis.RedisServer;
import com.stu.cx.star.Util.OtpUtil;
import com.stu.cx.star.Validation.ValidationResult;
import com.stu.cx.star.Validation.Validator;
import com.stu.cx.star.Controller.Vo.LoginVo;
import com.stu.cx.star.Dao.LoginMapper;
import com.stu.cx.star.Entity.Login;
import com.stu.cx.star.Exception.EmException;
import com.stu.cx.star.Exception.UserException;
import com.stu.cx.star.Service.UserService;
import com.stu.cx.star.Util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @Author: riskychan
 * @Description:用户相关接口实现
 * @Date: Create in 16:10 2019/9/18
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private Validator validator;
    @Resource
    private  LoginMapper loginMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private RedisServer redisServer;

    @Value("${mail.fromMail.sender}")
    private String sender;

    @Override
    public void login(LoginVo loginVo) throws UserException {
        ValidationResult result = validator.validate(loginVo);
        //校验参数
        if(result.isHasError()){
            throw new UserException(EmException.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        String mobile = loginVo.getMobile();
        Login login = loginMapper.selectByMobile(mobile);
        if(login == null){
            throw  new UserException(EmException.USER_NOT_EXIST);
        }
        String pass = MD5Util.encrpt(loginVo.getPassword());
        String dbpass = login.getPassword();
        if(!StringUtils.equals(pass,dbpass)){
            throw new UserException(EmException.PASSWORD_ERROR);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    public void getMailOtp(String mail) throws UserException {
        if(StringUtils.isEmpty(mail)){
            throw new UserException(EmException.MAIL_EMPTY);
        }
        //获取验证码
        String val = OtpUtil.getMailOtp();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(mail);
        message.setSubject("注册登录验证码");
        message.setText("您的验证码为："+val);
        try{
            redisServer.setRegisterOtp(mail,val);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new UserException(EmException.SEND_MAIL_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    public void register(RegisterVo registerVo) throws UserException {
        ValidationResult validationResult = validator.validate(registerVo);
        if(validationResult.isHasError()){
            throw  new UserException(EmException.PARAMETER_VALIDATION_ERROR,validationResult.getErrMsg());
        }
        //判断是否勾选已阅读协议
        if(registerVo.isAgreement()){
            //手机号已经存在
            Login isMobileLogin  = loginMapper.selectByMobile(registerVo.getMobile());
            if(isMobileLogin != null){
                throw new UserException(EmException.USER_ISEXIST);
            }
            //邮箱已经存在
            Login isMailLogin = loginMapper.selectByMail(registerVo.getMail());
            if(isMailLogin != null){
                throw new UserException(EmException.EMAIL_ISEXIST);
            }
            //获取缓存中的验证码
            String otp = redisServer.getRegisterOtp(registerVo.getMail());
            if(StringUtils.isEmpty(otp)){
                throw new UserException(EmException.OTP_OUT_TIME);
            } else if(StringUtils.equals(otp,registerVo.getOtp())){
                Login login = registerToLogin(registerVo);
                loginMapper.insertSelective(login);
            }else {
                throw new UserException(EmException.OTP_ERROR);
            }
        }else{
            throw new UserException(EmException.IS_NOT_AGREE);
        }

    }

    @Override
    public void getForgetOtp(String mail) throws UserException {
        //参数校验
        if(StringUtils.isEmpty(mail)){
            throw  new UserException(EmException.MAIL_EMPTY);
        }
        //判断输入的邮箱是否存在
        Login login = loginMapper.selectByMail(mail);
        if(login == null){
            logger.info("当前Login为null");
            throw  new UserException(EmException.MAIL_IS_NOTEXIST);
        }
        String otp = OtpUtil.getMailOtp();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject("找回密码的验证码");
        simpleMailMessage.setText("您的验证码为："+otp);
        try{
            redisServer.setFindPasswordOtp(mail,otp);
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void forgetPass(ForgetPasswordVo forgetPasswordVo) throws UserException {
        //对输入的参数先进行校验
        ValidationResult validationResult = validator.validate(forgetPasswordVo);
        if(validationResult.isHasError()){
            throw new UserException(EmException.PARAMETER_VALIDATION_ERROR,validationResult.getErrMsg());
        }
        //判断输入的邮箱是否存在
        Login login = loginMapper.selectByMail(forgetPasswordVo.getEmail());
        if(login == null){
            throw  new UserException(EmException.MAIL_IS_NOTEXIST);
        }
        //对验证码进行校验
        String otp = redisServer.getFindPasswordOtp(forgetPasswordVo.getEmail());
        if(StringUtils.isEmpty(otp)){
            throw new UserException(EmException.OTP_OUT_TIME,"验证码失效，请重新获取");
        } else if(!StringUtils.equals(otp,forgetPasswordVo.getOtp())){
            throw new UserException(EmException.OTP_ERROR);
        }
        //对密码进行加密
        String newPass = MD5Util.encrpt(forgetPasswordVo.getPassword());
        //更新对应的数据库
        loginMapper.updateByMail(forgetPasswordVo.getEmail(),newPass);
    }

    //Vo to Do
    public Login registerToLogin(RegisterVo registerVo){
        if(registerVo == null){
            return null;
        }
        //存入数据的密码需要先进行MD5加密
        String tempPass = MD5Util.encrpt(registerVo.getPassword());
        registerVo.setPassword(tempPass);
        Login login = new Login();
        BeanUtils.copyProperties(registerVo,login);
        //因为这里把盐给写死了
        login.setSalt("1a2b3c4d5f");
        return login;
    }
}
