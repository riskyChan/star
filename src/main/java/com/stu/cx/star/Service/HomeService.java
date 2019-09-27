package com.stu.cx.star.Service;

import com.stu.cx.star.Controller.Vo.ArticleVo;
import com.stu.cx.star.Controller.Vo.LoginLogVo;
import com.stu.cx.star.Controller.Vo.ShowArticleVo;
import com.stu.cx.star.Entity.Article;
import com.stu.cx.star.Exception.UserException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: riskychan
 * @Description:operate on home
 * @Date: Create in 14:44 2019/9/25
 */
public interface HomeService {

    //获得登陆日志
    public List<LoginLogVo> getLoginLog(String mobile,String startTime,String endTime) throws UserException;

    //发布文章
    public void publishArticle(ArticleVo articleVo, HttpServletRequest request) throws UserException;

    //获取该用户的文章列表
    public List<ShowArticleVo> getArticleList(HttpServletRequest request,Integer status);

    //删除文章
    public void deleteArtilce(HttpServletRequest request,Integer status,Integer... ids) throws UserException;
}
