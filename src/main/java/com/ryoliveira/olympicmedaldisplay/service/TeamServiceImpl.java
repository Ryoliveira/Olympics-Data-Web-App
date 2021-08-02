package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.springframework.stereotype.*;

@Service
public class TeamServiceImpl implements TeamService{

    private final DataScrapeService dataScrapeService;

    public TeamServiceImpl(DataScrapeService dataScrapeService){
        this.dataScrapeService = dataScrapeService;
    }


    @Override
    public TeamList getAllTeamStandings(String sport) {
        return this.dataScrapeService.getStandings(sport);
    }
}
