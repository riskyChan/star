package com.stu.cx.star.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import com.stu.cx.star.Annotation.LoginToken;
import com.stu.cx.star.Annotation.PassToken;
import com.stu.cx.star.Entity.Login;
import com.stu.cx.star.Exception.EmException;
import com.stu.cx.star.Exception.UserException;
import com.stu.cx.star.Redis.RedisServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: riskychan
 * @Description:
 * @Date: Create in 14:28 2019/9/24
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisServer redisServer;

    private Gson gson = null;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object object) throws UserException {
        String token = request.getHeader("token");
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)object;
        Method method = handlerMethod.getMethod();
        //has passtoken?
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        //has LoginToken?
        if(method.isAnnotationPresent(LoginToken.class)){
            LoginToken loginToken = (LoginToken)method.getAnnotation(LoginToken.class);
            if(loginToken.required()){
                if(token == null){
                    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
                    response.setHeader("Access-Control-Allow-Methods", "*");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.setHeader("Access-Control-Allow-Credentials", "true");
                    throw new UserException(EmException.NOT_TOOKEN);
                }
                String json = redisServer.getToken(token.toString());
                //json to object
                gson = new Gson();
                Login login = gson.fromJson(json,Login.class);
                if(login == null){
                    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
                    response.setHeader("Access-Control-Allow-Methods", "*");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.setHeader("Access-Control-Allow-Credentials", "true");
                    throw new UserException(EmException.TOOKEN_ERROR);
                }
                //to vailid token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(login.getPassword())).build();
                try{
                    jwtVerifier.verify(token);
                }catch (JWTCreationException e){
                    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
                    response.setHeader("Access-Control-Allow-Methods", "*");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.setHeader("Access-Control-Allow-Credentials", "true");
                    e.printStackTrace();
                }
                return  true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
