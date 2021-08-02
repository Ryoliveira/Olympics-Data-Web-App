package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.springframework.stereotype.*;

@Service
public class SportServiceImpl implements SportService{

    private final DataScrapeService dataScrapeService;

    public SportServiceImpl(DataScrapeService dataScrapeService){
        this.dataScrapeService = dataScrapeService;
    }


    @Override
    public SportsList getSportList() {
        return this.dataScrapeService.getSportsList();
    }
}
