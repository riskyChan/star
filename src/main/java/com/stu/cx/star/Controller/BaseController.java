package com.stu.cx.star.Controller;

import com.stu.cx.star.Exception.EmException;
import com.stu.cx.star.Exception.UserException;
import com.stu.cx.star.Response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: riskychan
 * @Description:异常的处理器
 * @Date: Create in 18:13 2019/9/18
 */
public class BaseController {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException(HttpServletRequest request,Exception ex){
        //必须自己封装data对象，否则data为exception反序列化的对象
        Map<String,Object> responseData = new HashMap<>();

        if(ex instanceof UserException){
            UserException bussinessException = (UserException) ex;
            responseData.put("errCode",bussinessException.getErrCode());
            responseData.put("errMsg",bussinessException.getErrMsg());
        } else {
            responseData.put("errCode", EmException.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg",EmException.UNKNOW_ERROR.getErrMsg());

        }
        return CommonReturnType.create(responseData,"fail");
    }
}
