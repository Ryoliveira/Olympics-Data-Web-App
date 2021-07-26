package com.ryoliveira.olympicmedaldisplay.util;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Service
public class DataScrape {

    Logger LOGGER = LoggerFactory.getLogger(DataScrape.class);

    @Value("${medal-standings-url}")
    private String medalStandingsUrl;

    public TeamList getStandings(){
        List<Team> teams = new ArrayList<>();

        try{
            Document doc = Jsoup.connect(medalStandingsUrl).get();
            Element medalTable = doc.select("table#medal-standing-table").get(0);

            //Iterate through each row and pull needed data
            if(medalTable != null){
                Elements rows = medalTable.select("tr");
                for(Element row : rows.subList(1, rows.size())){ // First row isn't needed
                    Team team = createTeam(row);
                    teams.add(team);
                }
            }
        }catch (IOException e){
            LOGGER.error(e.getMessage());
        }

        return new TeamList(teams);
    }

    public SportsList getSportsList(){

        List<String> sportsList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(medalStandingsUrl).get();

            Elements sportsDropdown = doc.select("ul.dropdown-menu");
            Elements sports = sportsDropdown.select("li");

            sports.forEach(sport -> sportsList.add(sport.text()));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return new SportsList(sportsList);
    }

    private Team createTeam(Element teamRow){
        Elements teamData = teamRow.select("td");

        int rank = Integer.parseInt(teamData.get(0).text());
        String teamName = teamData.get(1).text();
        int goldMedals = Integer.parseInt(teamData.get(2).text());
        int silverMedals = Integer.parseInt(teamData.get(3).text());
        int bronzeMedals = Integer.parseInt(teamData.get(4).text());
        int totalMedals = Integer.parseInt(teamData.get(5).text());
        int rankByTotalMedals = Integer.parseInt(teamData.get(6).text());
        String countryTag = teamData.get(7).text();

        return new Team(rank, countryTag, teamName, goldMedals, silverMedals, bronzeMedals, totalMedals, rankByTotalMedals);
    }




}
