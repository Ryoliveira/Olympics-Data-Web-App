package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class Tab {

    private String tabName;

    private List<Article> articleList;

    public Tab(String tabName, List<Article> articleList) {
        this.tabName = tabName;
        this.articleList = articleList;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public String toString() {
        return "Tab{" +
                "tabName='" + tabName + '\'' +
                ", articleList=" + articleList +
                '}';
    }
}
