package com.stu.cx.star.Controller;

import com.stu.cx.star.Annotation.LoginToken;
import com.stu.cx.star.Controller.Vo.LoginLogVo;
import com.stu.cx.star.Exception.UserException;
import com.stu.cx.star.Response.CommonReturnType;
import com.stu.cx.star.Service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: riskychan
 * @Description:home
 * @Date: Create in 14:39 2019/9/25
 */
@RestController
@RequestMapping("help")
public class HomeController extends BaseController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private HomeService homeService;

    @GetMapping("/getLoginLog")
    @LoginToken
    public CommonReturnType getLoginLog(@RequestParam(value = "mobile")String mobile,
                                        @RequestParam(value = "startTime")String startTime,
                                        @RequestParam(value = "endTime") String endTime) throws UserException {
        logger.info("startTime:"+startTime);
        logger.info("endTime:"+endTime);
        List<LoginLogVo> loginLogs = homeService.getLoginLog(mobile,startTime,endTime);
        return CommonReturnType.create(loginLogs);
    }
}
