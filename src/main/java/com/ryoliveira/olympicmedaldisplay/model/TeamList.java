package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class TeamList {

    private List<Team> teams;

    public TeamList(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "TeamList{" +
                "teams=" + teams +
                '}';
    }
}
