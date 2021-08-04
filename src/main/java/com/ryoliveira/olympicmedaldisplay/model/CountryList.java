package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class CountryList {

    private List<Country> countryList;

    public CountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
