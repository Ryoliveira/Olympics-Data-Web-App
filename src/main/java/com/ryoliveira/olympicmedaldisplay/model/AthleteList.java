package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class AthleteList {

    private List<Athlete> athletes;

    public AthleteList(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }
}
