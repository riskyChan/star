package com.stu.cx.star.Service.Impl;

import com.stu.cx.star.Controller.Vo.LoginLogVo;
import com.stu.cx.star.Dao.LoginLogMapper;
import com.stu.cx.star.Entity.LoginLog;
import com.stu.cx.star.Exception.EmException;
import com.stu.cx.star.Exception.UserException;
import com.stu.cx.star.Service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: riskychan
 * @Description:to achieve HomeService interface
 * @Date: Create in 14:44 2019/9/25
 */
@Service
public class HomeServiceImpl implements HomeService {
    Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);
    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public List<LoginLogVo> getLoginLog(String mobile,String startTime,String endTime) throws UserException {
        try{
            List<LoginLog> loginLogs = loginLogMapper.selectAll(mobile,startTime,endTime);
            List<LoginLogVo> loginLogVos = converLoginLogToLoginLogVo(loginLogs);
            logger.info("loginLogs length:"+loginLogVos.size());
            return loginLogVos;
        }catch (Exception e){
            throw new UserException(EmException.UNKNOW_ERROR);
        }

    }

    //LoginLog to LoginLogVo
    public List<LoginLogVo> converLoginLogToLoginLogVo(List<LoginLog> loginLogs){
        if(loginLogs.size() <= 0){
            return null;
        }else{
            List<LoginLogVo> loginLogVos = new LinkedList<LoginLogVo>();
            LoginLogVo loginLogVo = null;
            for(int i = 0;i<loginLogs.size();i++){
                loginLogVo = new LoginLogVo();
                BeanUtils.copyProperties(loginLogs.get(i),loginLogVo);
                loginLogVo.setKey(loginLogs.get(i).getId());
                loginLogVos.add(loginLogVo);
            }
            return  loginLogVos;
        }
    }
}
