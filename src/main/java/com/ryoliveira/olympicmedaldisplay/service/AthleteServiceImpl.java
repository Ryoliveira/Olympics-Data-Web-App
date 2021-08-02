package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;
import com.ryoliveira.olympicmedaldisplay.repo.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AthleteServiceImpl implements AthleteService {

    Logger LOGGER = LoggerFactory.getLogger(AthleteServiceImpl.class);

    private final AthleteRepository athleteRepo;

    public AthleteServiceImpl(AthleteRepository athleteRepo){
        this.athleteRepo = athleteRepo;
    }

    @Override
    public AthleteList getAllAthletes() {
        return new AthleteList(athleteRepo.findAll());
    }

    @Override
    public AthleteList getAllAthletesByCountry(String country) {
        return new AthleteList(this.athleteRepo.findAllByCountry(country));
    }

    @Override
    public AthleteList getAllAthletesByDiscipline(String sport) {
        return new AthleteList(this.athleteRepo.findAllByDiscipline(sport));
    }


    @Override
    public Athlete getAthleteByName(String name) {
        Optional<Athlete> optional  = this.athleteRepo.findByName(name);
        return optional.orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Athlete saveAthlete(Athlete athlete) {
        Athlete result = null;
        if(athlete != null && !this.athleteRepo.exists(Example.of(athlete))){
            result = this.athleteRepo.save(athlete);
            LOGGER.info("Athlete Saved.");
        }else{
            LOGGER.error("Athlete already in database or null");
        }
        return result;
    }
}
