package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Service
public class DataScrapeService {

    private final String BASE_URL = "https://olympics.com";
    Logger LOGGER = LoggerFactory.getLogger(DataScrapeService.class);

    public TeamList getStandings(String sport) {
        sport = sport.toLowerCase().replaceAll("\\s+", "-");
        LOGGER.info(sport);
        String medalStandingsUrl = String.format("%s/tokyo-2020/olympic-games/en/results/%s/medal-standings.htm", BASE_URL, sport);
        List<Team> teams = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(medalStandingsUrl).get();
            Elements tables = doc.select("table#medal-standing-table");
            if (tables.size() > 0) { // No tables will be found if no stats are recorded for given sport
                Element medalTable = tables.get(0);
                Elements rows = medalTable.select("tr");
                //Iterate through each row and pull needed data
                for (Element row : rows.subList(1, rows.size())) { // First row isn't needed
                    Team team = createTeam(row);
                    teams.add(team);
                }
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.error(e.getMessage());
        }

        return new TeamList(teams);
    }

    public SportsList getSportsList() {

        String sportsUrl = String.format("%s/tokyo-2020/en/sports/", BASE_URL);

        List<String> sportsList = new ArrayList<>();
        sportsList.add("All Sports");

        try {
            Document doc = Jsoup.connect(sportsUrl).get();
            Elements sports = doc.select("h2.tk-disciplines__title");
            sports.forEach(sport -> sportsList.add(sport.text()));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return new SportsList(sportsList);
    }

    private Team createTeam(Element teamRow) {
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
