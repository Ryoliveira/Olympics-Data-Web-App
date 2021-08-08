package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class Article {

    private String title;
    private List<String> contents;

    public Article(String title, List<String> contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", contents=" + contents +
                '}';
    }
}
