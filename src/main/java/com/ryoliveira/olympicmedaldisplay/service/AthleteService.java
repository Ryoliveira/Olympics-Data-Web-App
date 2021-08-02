package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;

public interface AthleteService {

    AthleteList getAllAthletes();
    AthleteList getAllAthletesByCountry(String country);
    AthleteList getAllAthletesByDiscipline(String sport);
    Athlete getAthleteByName(String name);
    Athlete saveAthlete(Athlete athlete);


}
