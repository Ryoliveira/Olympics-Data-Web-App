package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class SportInformation {

    private String sportName;
    private List<Tab> tabs;

    public SportInformation(String sportName, List<Tab> tabs) {
        this.sportName = sportName;
        this.tabs = tabs;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    @Override
    public String toString() {
        return "SportInformation{" +
                "sportName='" + sportName + '\'' +
                ", tabs=" + tabs +
                '}';
    }
}
