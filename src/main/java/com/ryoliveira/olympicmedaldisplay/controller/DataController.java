package com.ryoliveira.olympicmedaldisplay.controller;

import com.ryoliveira.olympicmedaldisplay.model.*;
import com.ryoliveira.olympicmedaldisplay.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DataController {

    private final DataScrapeServiceImpl dataScrapeService;
    private final AthleteService athleteService;

    public DataController(DataScrapeServiceImpl dataScrapeService, AthleteService athleteService){
        this.dataScrapeService = dataScrapeService;
        this.athleteService = athleteService;
    }

    @GetMapping("/{sport}/standings")
    public TeamList getStandings(@PathVariable String sport){
        return this.dataScrapeService.getStandings(sport);
    }

    @GetMapping("/sports")
    public SportsList getSportsList(){
        return this.dataScrapeService.getSportsList();
    }

    @GetMapping("/athletes")
    public AthleteList getAllAthletes(){
        return this.athleteService.getAllAthletes();
    }

    @GetMapping("/team/{country}/athletes")
    public AthleteList getAllAthletesByCountry(@PathVariable String country){
        return this.athleteService.getAllAthletesByCountry(country);
    }

    @GetMapping("/{sport}/athletes")
    public AthleteList getAllAthletesByDiscipline(@PathVariable String sport){
        return this.athleteService.getAllAthletesByDiscipline(sport);
    }

    @GetMapping("/athlete/{name}")
    public Athlete getAthleteByName(@PathVariable String name){
        return this.athleteService.getAthleteByName(name);
    }



}
