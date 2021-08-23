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

    private final AthleteMongoRepository athleteRepo;

    public AthleteServiceImpl(AthleteMongoRepository athleteRepo){
        this.athleteRepo = athleteRepo;
    }

    @Override
    public Page<Athlete> getAllAthletes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return athleteRepo.findAll(pageable);
    }

    @Override
    public Page<Athlete> getAllAthletesByCountry(String country, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.athleteRepo.findAllByCountry(country, pageable);
    }

    @Override
    public Page<Athlete> getAllAthletesByDiscipline(String sport, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.athleteRepo.findAllByDiscipline(sport, pageable);
    }

    @Override
    public Page<Athlete> getAthletesBySportAndCountry(String sport, String country, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.athleteRepo.findAllByDisciplineAndCountry(sport, country, pageable);
    }


    @Override
    public Athlete getAthleteByName(String name) {
        Athlete athlete = null;

        try{
            Optional<Athlete> optional  = this.athleteRepo.findByName(name);
            athlete = optional.orElseThrow(NoSuchElementException::new);
        }catch (NoSuchElementException e){
            LOGGER.error("getAthleteByName(String name): " + e.getMessage());
        }
        return athlete;
    }

    @Override
    public Athlete saveAthlete(Athlete athlete) {
        Athlete result = null;
        if(athlete != null && !this.athleteRepo.exists(Example.of(athlete))){
            result = this.athleteRepo.save(athlete);
            LOGGER.info(result.toString());
            LOGGER.info("Athlete Saved.");
        }else{
            LOGGER.error("Athlete already in database or null");
        }
        return result;
    }
}
