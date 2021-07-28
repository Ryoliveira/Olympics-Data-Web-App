package com.ryoliveira.olympicmedaldisplay.controller;

import com.ryoliveira.olympicmedaldisplay.model.*;
import com.ryoliveira.olympicmedaldisplay.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DataController {

    private final DataScrapeServiceImpl dataScrapeService;

    public DataController(DataScrapeServiceImpl dataScrapeService){
        this.dataScrapeService = dataScrapeService;
    }

    @GetMapping("/{sport}/standings")
    public TeamList getStandings(@PathVariable String sport){
        return this.dataScrapeService.getStandings(sport);
    }

    @GetMapping("/sports")
    public SportsList getSportsList(){
        return this.dataScrapeService.getSportsList();
    }


}
