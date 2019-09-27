package com.stu.cx.star.Service.Impl;

import com.google.gson.Gson;
import com.stu.cx.star.Controller.Vo.ArticleVo;
import com.stu.cx.star.Controller.Vo.LoginLogVo;
import com.stu.cx.star.Controller.Vo.ShowArticleVo;
import com.stu.cx.star.Dao.ArticleMapper;
import com.stu.cx.star.Dao.LoginLogMapper;
import com.stu.cx.star.Entity.Article;
import com.stu.cx.star.Entity.Login;
import com.stu.cx.star.Entity.LoginLog;
import com.stu.cx.star.Exception.EmException;
import com.stu.cx.star.Exception.UserException;
import com.stu.cx.star.Redis.RedisServer;
import com.stu.cx.star.Service.HomeService;
import com.stu.cx.star.Util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
    @Autowired
    private Gson gson;
    @Autowired
    private RedisServer redisServer;
    @Resource
    private ArticleMapper articleMapper;

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

    @Override
    public void publishArticle(ArticleVo articleVo, HttpServletRequest request) throws UserException {

            Login login = getUserByToken(request);
            Article article = convertArticleVoToArticle(articleVo,login.getId());

            //草稿状态写进数据库不，不做内容标题的非空判断
            if(articleVo.getStatus() == 0){articleMapper.insertSelective(article);
                String data = gson.toJson(article);
                redisServer.setEditingArticle(login.getMobile(),data);
            }else{
                //先做非空判断
                if(StringUtils.isEmpty(article.getArticleTitle())){
                    throw new UserException(EmException.PARAMETER_VALIDATION_ERROR,"请输入文章标题");
                }

                if(StringUtils.isEmpty(article.getArticleContent())){
                    throw new UserException(EmException.PARAMETER_VALIDATION_ERROR,"请输入文章内容");
                }
                articleMapper.insertSelective(article);
                String data = gson.toJson(article);
                redisServer.setUserArticle(login.getMobile(),data);
            }
    }

    @Override
    public List<ShowArticleVo> getArticleList(HttpServletRequest request,Integer status) {
        Login login = getUserByToken(request);
        List<String> list = null;
        //草稿还是已发布
        if(status == 0){
            list = redisServer.getEditingArticle(login.getMobile());
        }else{
            list = redisServer.getUserArticle(login.getMobile());
        }
        //将json格式转化成Article，这里用迭代器的方式，因为要根据时间，所以用ListIterator逆向
        List<ShowArticleVo> articleList = new LinkedList<>();
        Article article = null;
        ShowArticleVo showArticleVo = null;
        ListIterator listIterator = list.listIterator();
        //先把迭代器指向最后
        while(listIterator.hasNext()){
            listIterator.next();
        }
        while (listIterator.hasPrevious()){
            article = gson.fromJson(listIterator.previous().toString(),Article.class);
            showArticleVo = convertArticlToShowArtilce(article,login.getName());
            articleList.add(showArticleVo);
        }
        return articleList;
    }

    @Override
    public void deleteArtilce(HttpServletRequest request, Integer status, Integer... ids) throws UserException {
        Login login = getUserByToken(request);
        String mobile = login.getMobile();
        if(ids == null || ids.length == 0){
            throw new UserException(EmException.PARAMETER_VALIDATION_ERROR,"请选择要删除的文章");
        }
        if(status == 0){
            //删除草稿状态文章
            for(int i = 0;i<ids.length;i++){
                articleMapper.deleteByPrimaryKey(ids[i]);
                deleteEditArt(mobile,ids[i]);
            }
        }else {
            //删除已发布的文章
            for(int i =0;i<ids.length;i++){
                articleMapper.deleteByPrimaryKey(ids[i]);
                deletePubArt(mobile,ids[i]);
            }
        }
    }

    //根据token获取用户信息
    public Login getUserByToken(HttpServletRequest request){

        /*
                从header中得到token，并在缓存中根据token得到用户的信息
                这里不需要进行非空判断，因为在拦截其中有做处理
         */
        String token = request.getHeader("token");
        String data = redisServer.getToken(token);
        gson = new Gson();
        Login login = gson.fromJson(data,Login.class);
        return login;
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

    //ArticleVo to Article
    public Article convertArticleVoToArticle(ArticleVo articleVo,Integer id){
        if(articleVo == null){
            return null;
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleVo,article);
        article.setPublishTime(DateUtil.getCurrentTime());
        article.setPublisherId(id);
        return article;
    }

    //Article to ShowArtilce
    public ShowArticleVo convertArticlToShowArtilce(Article article,String name){
        if(article == null){
            return null;
        }
        ShowArticleVo showArticleVo = new ShowArticleVo();
        BeanUtils.copyProperties(article,showArticleVo);
        showArticleVo.setUserName(name);
        return showArticleVo;
    }

    //找到redis中对应的文章并删除，由于不想在redis中将list转为map，这里有优化的空间，我自己都嫌弃这种方式，但我目前不想思考改进方法
    public void deleteEditArt(String mobile,Integer id){
        List<String> list = redisServer.getEditingArticle(mobile);
        Iterator iterator = list.iterator();
        Article article = null;
        while(iterator.hasNext()){
            String temp = iterator.next().toString();
            gson = new Gson();
            article = gson.fromJson(temp,Article.class);
            if(article.getId().equals(id)){
                redisServer.deleteEditingArticle(mobile,temp);
            }
        }
    }

    //同上
    public void deletePubArt(String mobile,Integer id){
        List<String> list = redisServer.getUserArticle(mobile);
        Iterator iterator = list.iterator();
        Article article = null;
        while(iterator.hasNext()){
            String temp = iterator.next().toString();
            gson = new Gson();
            article = gson.fromJson(temp,Article.class);
            if(article.getId().equals(id)){
                redisServer.deletePublishArticle(mobile,temp);
            }
        }
    }
}
