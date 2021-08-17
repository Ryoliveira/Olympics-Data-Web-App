package com.ryoliveira.olympicmedaldisplay.controller;

import com.ryoliveira.olympicmedaldisplay.model.*;
import com.ryoliveira.olympicmedaldisplay.service.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DataController {

    private final AthleteService athleteService;
    private final TeamService teamService;
    private final SportService sportService;
    private final CountryService countryService;

    public DataController(AthleteService athleteService, TeamService teamService,
                          SportService sportService, CountryService countryService) {
        this.athleteService = athleteService;
        this.teamService = teamService;
        this.sportService = sportService;
        this.countryService = countryService;
    }

    @GetMapping("/{sport}/standings")
    public ResponseEntity<TeamList> getStandings(@PathVariable String sport) {
        return new ResponseEntity<>(this.teamService.getAllTeamStandings(sport), HttpStatus.OK);
    }

    @GetMapping("/sports")
    public ResponseEntity<SportsList> getSportsList() {
        return new ResponseEntity<>(this.sportService.getSportList(), HttpStatus.OK);
    }

    @GetMapping("/athletes")
    public ResponseEntity<Page<Athlete>> getAllAthletes(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(this.athleteService.getAllAthletes(page, size), HttpStatus.OK);
    }

    @GetMapping("/team/{country}/athletes")
    public ResponseEntity<Page<Athlete>> getAllAthletesByCountry(@PathVariable String country, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(this.athleteService.getAllAthletesByCountry(country, page, size), HttpStatus.FOUND);
    }

    @GetMapping("/{sport}/athletes")
    public ResponseEntity<Page<Athlete>> getAllAthletesByDiscipline(@PathVariable String sport, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(this.athleteService.getAllAthletesByDiscipline(sport, page, size), HttpStatus.FOUND);
    }

    @GetMapping("/athlete/{name}")
    public ResponseEntity<Athlete> getAthleteByName(@PathVariable String name) {
        return new ResponseEntity<>(this.athleteService.getAthleteByName(name), HttpStatus.FOUND);
    }

    @GetMapping("/{sport}/{country}/athletes")
    public ResponseEntity<Page<Athlete>> getAthletesBySportAndCountry(@PathVariable("sport") String sport, @PathVariable("country") String country,
                                                                      @RequestParam("page") int pageNumber, @RequestParam("size") int pageSize) {
        return new ResponseEntity<>(this.athleteService.getAthletesBySportAndCountry(sport, country, pageNumber, pageSize), HttpStatus.FOUND);
    }

    @GetMapping("/countries")
    public ResponseEntity<CountryList> getAllCountries() {
        return new ResponseEntity<>(this.countryService.getCountryList(), HttpStatus.OK);
    }

    @GetMapping("/{sport}/information")
    public ResponseEntity<SportInformation> getSportInformation(@PathVariable String sport) {
        return new ResponseEntity<>(this.sportService.getSportInformation(sport), HttpStatus.FOUND);
    }

    @GetMapping("/information/{country}")
    public ResponseEntity<CountryInformation> getCountryInformation(@PathVariable String country) {
        return new ResponseEntity<>(this.countryService.getCountryInformation(country), HttpStatus.FOUND);
    }

}
