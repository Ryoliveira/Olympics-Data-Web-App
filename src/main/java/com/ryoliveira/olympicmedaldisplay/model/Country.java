package com.ryoliveira.olympicmedaldisplay.model;

public class Country {

    private String countryName;

    private String countryFlagUrl;

    private String profilePageStrId;

    public Country(String countryName, String countryFlagUrl, String profilePageStrId) {
        this.countryName = countryName;
        this.countryFlagUrl = countryFlagUrl;
        this.profilePageStrId = profilePageStrId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getProfilePageStrId() {
        return profilePageStrId;
    }

    public void setProfilePageStrId(String profilePageStrId) {
        this.profilePageStrId = profilePageStrId;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", countryFlagUrl='" + countryFlagUrl + '\'' +
                ", profilePageStrId='" + profilePageStrId + '\'' +
                '}';
    }
}
