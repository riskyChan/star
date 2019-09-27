package com.stu.cx.star.Redis;

import com.stu.cx.star.Exception.EmException;
import com.stu.cx.star.Exception.UserException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @Author: riskychan
 * @Description:to operate on redis
 * @Date: Create in 10:33 2019/9/20
 */
@Service
public class RedisServer {

    Logger logger = LoggerFactory.getLogger(RedisServer.class);

    private final static String REGISTER_OTP = "register_otp";
    private final static String FIND_PASSWORD_OTP = "forget_password_otp";
    private final static String USER_TOKEN = "user_token";
    private final static String USER_ARTICLE = "user_article_finished";
    private final static String EDITING_USER_ARTICLE = "user_article_editing";

    //token exists time
    private final static int TOKEN_TIME = 3600*24*2;

    @Autowired
    JedisPool jedisPool;
    Jedis jedis = null;

    //set register otp
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

    //get register otp
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

    //find password otp
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

    //login token
    public boolean setToken(String token,String data){
        try{
            jedis = jedisPool.getResource();
            jedis.expire(USER_TOKEN,TOKEN_TIME);
            jedis.hset(USER_TOKEN,token,data);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return false;
    }

    //get user data by token
    public String getToken(String token){
        try{
            jedis = jedisPool.getResource();
            String data = jedis.hget(USER_TOKEN,token);
            return data;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return null;
    }

    //设置发布的文章到缓存，后面几个方法和这可以优化整合成2个方法，后期抽空处理
    public boolean setUserArticle(String mobile,String data){
        try{
            jedis = jedisPool.getResource();
            String newKey = USER_ARTICLE+"_"+mobile;
            jedis.rpush(newKey,data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return false;
    }

    //获取缓存里发布的文章
    public List<String> getUserArticle(String mobile){
        try{
            jedis = jedisPool.getResource();
            String newKey = USER_ARTICLE+"_"+mobile;
            List<String> list = jedis.lrange(newKey,0,-1);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return null;
    }

    //将草稿状态的文章写到缓存
    public boolean setEditingArticle(String mobile,String data){
        try{
            jedis = jedisPool.getResource();
            String newKey = EDITING_USER_ARTICLE+"_"+mobile;
            jedis.rpush(newKey,data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return false;
    }

    //获取缓存里的草稿文章
    public List<String> getEditingArticle(String mobile){
        try{
            jedis = jedisPool.getResource();
            String newKey = EDITING_USER_ARTICLE+"_"+mobile;
            List<String> list = jedis.lrange(newKey,0,-1);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return null;
    }

    //删除草稿状态的文章
    public boolean deleteEditingArticle(String mobile,String data){
        try {
            jedis = jedisPool.getResource();
            String newKey = EDITING_USER_ARTICLE+"_"+mobile;
            jedis.lrem(newKey,0,data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return false;
    }

    //删除已发布的文章
    public boolean deletePublishArticle(String mobile,String data){
        try {
            jedis = jedisPool.getResource();
            String newKey = USER_ARTICLE+"_"+mobile;
            jedis.lrem(newKey,0,data);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return false;
    }
}
