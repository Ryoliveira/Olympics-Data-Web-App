package com.ryoliveira.olympicmedaldisplay.model;

public class SubMedalStanding {

    private String category;

    private int goldMedalCount;

    private int silverMedalCount;

    private int bronzeMedalCount;

    private int totalMedalCount;

    public SubMedalStanding(String category, int goldMedalCount, int silverMedalCount, int bronzeMedalCount, int totalMedalCount) {
        this.category = category;
        this.goldMedalCount = goldMedalCount;
        this.silverMedalCount = silverMedalCount;
        this.bronzeMedalCount = bronzeMedalCount;
        this.totalMedalCount = totalMedalCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getTotalMedalCount() {
        return totalMedalCount;
    }

    public void setTotalMedalCount(int totalMedalCount) {
        this.totalMedalCount = totalMedalCount;
    }

    @Override
    public String toString() {
        return "SubMedalStanding{" +
                "category='" + category + '\'' +
                ", goldMedalCount=" + goldMedalCount +
                ", silverMedalCount=" + silverMedalCount +
                ", bronzeMedalCount=" + bronzeMedalCount +
                ", totalMedalCount=" + totalMedalCount +
                '}';
    }
}
