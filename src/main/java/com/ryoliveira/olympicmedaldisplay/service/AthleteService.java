package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.springframework.data.domain.*;

public interface AthleteService {

    Page<Athlete> getAllAthletes(int page, int size);
    Page<Athlete> getAllAthletesByCountry(String country, int page, int size);
    Page<Athlete> getAllAthletesByDiscipline(String sport, int page, int size);
    Athlete getAthleteByName(String name);
    Athlete saveAthlete(Athlete athlete);


}
