package com.ryoliveira.olympicmedaldisplay.model;

public class Country {

    private String countryName;

    private String countryFlagUrl;

    public Country(String countryName, String countryFlagUrl) {
        this.countryName = countryName;
        this.countryFlagUrl = countryFlagUrl;
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

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", countryFlagUrl='" + countryFlagUrl + '\'' +
                '}';
    }
}
