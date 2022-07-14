package com.nascodefy.emosianpkm.Model;

public class Article {

    private String ArticleTitle;
    private String ArticleDesc;
    private String imageURL;

    public Article(String articleTitle, String articleDesc, String imageURL) {
        ArticleTitle = articleTitle;
        ArticleDesc = articleDesc;
        this.imageURL = imageURL;
    }

    public Article() {

    }

    public String getArticleTitle() {
        return ArticleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        ArticleTitle = articleTitle;
    }

    public String getArticleDesc() {
        return ArticleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        ArticleDesc = articleDesc;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}