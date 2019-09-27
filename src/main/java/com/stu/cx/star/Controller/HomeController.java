package com.stu.cx.star.Controller;

import com.stu.cx.star.Annotation.LoginToken;
import com.stu.cx.star.Controller.Vo.ArticleVo;
import com.stu.cx.star.Controller.Vo.LoginLogVo;
import com.stu.cx.star.Controller.Vo.ShowArticleVo;
import com.stu.cx.star.Entity.Article;
import com.stu.cx.star.Exception.UserException;
import com.stu.cx.star.Response.CommonReturnType;
import com.stu.cx.star.Service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/publishArticle")
    @LoginToken
    @Transactional(rollbackFor = Exception.class)
    public CommonReturnType publishArticle(@RequestBody ArticleVo articleVo, HttpServletRequest request) throws UserException {
        homeService.publishArticle(articleVo,request);
        return CommonReturnType.create(null);
    }

    @GetMapping("/getArticle")
    @LoginToken
    public CommonReturnType getArticle(HttpServletRequest request,@RequestParam(value = "status")Integer status){
        List<ShowArticleVo> list = homeService.getArticleList(request,status);
        return CommonReturnType.create(list);
    }

    @GetMapping("/deleteArticle")
    @LoginToken
    @Transactional(rollbackFor = Exception.class)
    public CommonReturnType deleteArticle(HttpServletRequest request,
                                          @RequestParam(value = "status")Integer status,
                                          @RequestParam(value = "ids")Integer... ids) throws UserException {
        homeService.deleteArtilce(request,status,ids);
        return CommonReturnType.create(null);
    }
}
