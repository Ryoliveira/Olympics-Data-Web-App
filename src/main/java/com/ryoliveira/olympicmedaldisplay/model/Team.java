package com.ryoliveira.olympicmedaldisplay.model;

public class Team {

    private int rank;

    private String country;

    private String teamName;

    private int goldMedalCount;

    private int silverMedalCount;

    private int bronzeMedalCount;

    private int totalMedals;

    private int rankByTotalMedals;

    public Team() {
    }

    public Team(int rank, String country, String teamName, int goldMedalCount, int silverMedalCount, int bronzeMedalCount, int totalMedals, int rankByTotalMedals) {
        this.rank = rank;
        this.country = country;
        this.teamName = teamName;
        this.goldMedalCount = goldMedalCount;
        this.silverMedalCount = silverMedalCount;
        this.bronzeMedalCount = bronzeMedalCount;
        this.totalMedals = totalMedals;
        this.rankByTotalMedals = rankByTotalMedals;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getGoldMedalCount() {
        return goldMedalCount;
    }

    public void setGoldMedalCount(int goldMedalCount) {
        this.goldMedalCount = goldMedalCount;
    }

    public int getSilverMedalCount() {
        return silverMedalCount;
    }

    public void setSilverMedalCount(int silverMedalCount) {
        this.silverMedalCount = silverMedalCount;
    }

    public int getBronzeMedalCount() {
        return bronzeMedalCount;
    }

    public void setBronzeMedalCount(int bronzeMedalCount) {
        this.bronzeMedalCount = bronzeMedalCount;
    }

    public int getTotalMedals() {
        return totalMedals;
    }

    public void setTotalMedals(int totalMedals) {
        this.totalMedals = totalMedals;
    }

    public int getRankByTotalMedals() {
        return rankByTotalMedals;
    }

    public void setRankByTotalMedals(int rankByTotalMedals) {
        this.rankByTotalMedals = rankByTotalMedals;
    }

    @Override
    public String toString() {
        return "Team{" +
                "rank=" + rank +
                ", country='" + country + '\'' +
                ", teamName='" + teamName + '\'' +
                ", goldMedalCount=" + goldMedalCount +
                ", silverMedalCount=" + silverMedalCount +
                ", bronzeMedalCount=" + bronzeMedalCount +
                ", totalMedals=" + totalMedals +
                ", rankByTotalMedals=" + rankByTotalMedals +
                '}';
    }
}
