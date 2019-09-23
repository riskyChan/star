package com.stu.cx.star.Redis;

import com.stu.cx.star.Exception.EmException;
import com.stu.cx.star.Exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: riskychan
 * @Description:自己构建redis的操作，而不是用jedis
 * @Date: Create in 10:33 2019/9/20
 */
@Service
public class RedisServer {


    private final static String REGISTER_OTP = "register_otp";
    private final static String FIND_PASSWORD_OTP = "forget_password_otp";

    @Autowired
    JedisPool jedisPool;
    Jedis jedis = null;

    //设置注册验证码
    public boolean setRegisterOtp(String mobile,String otp){
        try{
            jedis = jedisPool.getResource();
            jedis.setex(REGISTER_OTP+"_"+mobile,60,otp);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return false;
    }

    //获取登录的验证码
    public String getRegisterOtp(String mobile){
        try{
            jedis = jedisPool.getResource();
            String otp =null;
            otp = jedis.get(REGISTER_OTP+"_"+mobile);
            return otp;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return null;
    }

    //设置找回密码的验证码
    public boolean setFindPasswordOtp(String moblie,String otp){
        try{
            jedis = jedisPool.getResource();
            jedis.setex(FIND_PASSWORD_OTP+"_"+moblie,60,otp);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return false;
    }

    //获取找回密码的验证码
    public String getFindPasswordOtp(String mobile){
        try{
            jedis = jedisPool.getResource();
            String otp = null;
            otp = jedis.get(FIND_PASSWORD_OTP+"_"+mobile);
            return otp;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return null;
    }
}
