package com.ryoliveira.olympicmedaldisplay.controller;

import com.ryoliveira.olympicmedaldisplay.model.*;
import com.ryoliveira.olympicmedaldisplay.service.*;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DataController {

    private final AthleteService athleteService;
    private final TeamService teamService;
    private final SportService sportService;

    public DataController(DataScrapeServiceImpl dataScrapeService, AthleteService athleteService,
                          TeamService teamService, SportService sportService){
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
    public Page<Athlete> getAllAthletes(@RequestParam("page") int page, @RequestParam("size") int size){
        return this.athleteService.getAllAthletes(page, size);
    }

    @GetMapping("/team/{country}/athletes")
    public Page<Athlete> getAllAthletesByCountry(@PathVariable String country, @RequestParam("page") int page, @RequestParam("size") int size){
        return this.athleteService.getAllAthletesByCountry(country, page, size);
    }

    @GetMapping("/{sport}/athletes")
    public Page<Athlete> getAllAthletesByDiscipline(@PathVariable String sport, @RequestParam("page") int page, @RequestParam("size") int size){
        return this.athleteService.getAllAthletesByDiscipline(sport, page, size);
    }

    @GetMapping("/athlete/{name}")
    public Athlete getAthleteByName(@PathVariable String name){
        return this.athleteService.getAthleteByName(name);
    }



}
