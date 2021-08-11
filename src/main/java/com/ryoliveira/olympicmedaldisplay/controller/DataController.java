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
    private final CountryService countryService;

    public DataController(AthleteService athleteService, TeamService teamService,
                          SportService sportService, CountryService countryService){
        this.athleteService = athleteService;
        this.teamService = teamService;
        this.sportService = sportService;
        this.countryService = countryService;
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

    @GetMapping("/{sport}/{country}/athletes")
    public Page<Athlete> getAthletesBySportAndCountry(@PathVariable("sport") String sport, @PathVariable("country") String country,
                                                      @RequestParam("page") int pageNumber, @RequestParam("size") int pageSize){
        return this.athleteService.getAthletesBySportAndCountry(sport, country, pageNumber, pageSize);
    }

    @GetMapping("/countries")
    public CountryList getAllCountries(){
        return this.countryService.getCountryList();
    }

    @GetMapping("/{sport}/information")
    public SportInformation getSportInformation(@PathVariable String sport){
        return this.sportService.getSportInformation(sport);
    }

    @GetMapping("/information/{country}")
    public CountryInformation getCountryInformation(@PathVariable String country){
        return this.countryService.getCountryInformation(country);
    }



}
