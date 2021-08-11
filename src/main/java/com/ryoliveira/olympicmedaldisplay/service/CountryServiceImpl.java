package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.springframework.stereotype.*;

@Service
public class CountryServiceImpl implements CountryService{

    private final DataScrapeService dataScrapeService;

    public CountryServiceImpl(DataScrapeService dataScrapeService){
        this.dataScrapeService = dataScrapeService;
    }

    @Override
    public CountryList getCountryList() {
        return this.dataScrapeService.getCountryList();
    }

    @Override
    public CountryInformation getCountryInformation(String country) {
        return this.dataScrapeService.scrapeCountryInformation(country);
    }
}
