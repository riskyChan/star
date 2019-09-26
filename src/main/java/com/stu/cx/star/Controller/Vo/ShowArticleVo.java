package com.stu.cx.star.Controller.Vo;

/**
 * @Author: riskychan
 * @Description:to show Article List in Page
 * @Date: Create in 17:52 2019/9/26
 */
public class ShowArticleVo {
    private String articleTitle;
    private String articleContent;
    private String publishTime;
    private Integer status;

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
