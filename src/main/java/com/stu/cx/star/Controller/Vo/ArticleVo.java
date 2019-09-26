package com.stu.cx.star.Controller.Vo;

/**
 * @Author: riskychan
 * @Description: article object from page
 * @Date: Create in 15:32 2019/9/26
 */
public class ArticleVo {
    private String articleTitle;
    private String articleContent;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
