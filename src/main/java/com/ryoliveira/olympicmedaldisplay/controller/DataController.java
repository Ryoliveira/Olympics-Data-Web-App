package com.ryoliveira.olympicmedaldisplay.controller;

import com.ryoliveira.olympicmedaldisplay.model.*;
import com.ryoliveira.olympicmedaldisplay.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DataController {

    private final DataScrapeServiceImpl dataScrapeService;
    private final AthleteService athleteService;
    private final TeamService teamService;
    private final SportService sportService;

    public DataController(DataScrapeServiceImpl dataScrapeService, AthleteService athleteService,
                          TeamService teamService, SportService sportService){
        this.dataScrapeService = dataScrapeService;
        this.athleteService = athleteService;
        this.teamService = teamService;
        this.sportService = sportService;
    }

    @GetMapping("/{sport}/standings")
    public TeamList getStandings(@PathVariable String sport){
        return this.teamService.getAllTeamStandings(sport);
    }

    @GetMapping("/sports")
    public SportsList getSportsList(){
        return this.sportService.getSportList();
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
