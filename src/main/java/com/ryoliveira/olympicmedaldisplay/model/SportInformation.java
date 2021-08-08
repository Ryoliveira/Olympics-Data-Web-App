package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class SportInformation {

    List<Article> articleList;


    public SportInformation(List<Article> articleList) {
        this.articleList = articleList;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
