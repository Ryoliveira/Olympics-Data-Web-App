package com.ryoliveira.olympicmedaldisplay.model;

public class Sport {

    private String sportName;

    private String sportIconUrl;

    public Sport(){

    }

    public Sport(String sportName, String sportIconUrl){
        this.sportName = sportName;
        this.sportIconUrl = sportIconUrl;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportIconUrl() {
        return sportIconUrl;
    }

    public void setSportIconUrl(String sportIconUrl) {
        this.sportIconUrl = sportIconUrl;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "sportName='" + sportName + '\'' +
                ", sportIconUrl='" + sportIconUrl + '\'' +
                '}';
    }
}
