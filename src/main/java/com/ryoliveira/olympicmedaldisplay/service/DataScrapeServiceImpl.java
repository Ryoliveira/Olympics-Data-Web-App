package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;
import com.ryoliveira.olympicmedaldisplay.repo.*;
import com.ryoliveira.olympicmedaldisplay.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Service
public class DataScrapeServiceImpl implements DataScrapeService {

    private final String BASE_URL = "https://olympics.com";
    Logger LOGGER = LoggerFactory.getLogger(DataScrapeServiceImpl.class);

    private final AthleteRepository athleteRepo;

    public DataScrapeServiceImpl(AthleteRepository athleteRepo){
        this.athleteRepo = athleteRepo;
    }

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

    @Override
    public AthleteList getAthletes(String sport) {
        sport = sport.toLowerCase().replaceAll("\\s+", "-");
        String athleteListUrl = String.format("%s/tokyo-2020/olympic-games/en/results/%s/athletes.htm", BASE_URL, sport);

        List<Athlete> athletes = new ArrayList<>();

        List<String> htmlPages = new SeleniumUtil().renderPages(athleteListUrl);
        for (String page : htmlPages) {
            Document doc = Jsoup.parse(page);
            Elements playerTags = doc.select("div.playerTag");
            for (Element playerTag : playerTags) {
                Element athletePageLink = playerTag.selectFirst("a[href]");
                Athlete createdAthlete = createAthlete(athletePageLink.attr("href").substring(9));
                if(createdAthlete != null && !athleteRepo.exists(Example.of(createdAthlete))){
                    athleteRepo.save(createdAthlete);
                    athletes.add(createdAthlete);
                }else{
                    LOGGER.info("Athlete already in database");
                }
            }
            if (playerTags.size() == 0) {
                LOGGER.info("No Tags!!!");
            }
        }
        return new AthleteList(athletes);
    }

    private Athlete createAthlete(String athletePageUrl) {
        String url = String.format("%s/tokyo-2020/olympic-games/%s", BASE_URL, athletePageUrl);

        Athlete athlete = null;

        String name;
        String photoUrl;
        String country;
        String countryFlagUrl;
        String discipline;
        String dob;
        int age;
        String gender;
        String heightMeterAndFoot = null;
        String placeOfBirth = null;
        String birthCountry = null;
        String placeOfResidence = null;
        String residenceCountry = null;

        try {
            Document doc = Jsoup.connect(url).get();
            name = doc.selectFirst("h1").text();

            Element playerBioPanel = doc.selectFirst("div.panel-bio");

            photoUrl = playerBioPanel.selectFirst("img").attr("src").substring(9);

            Elements rows = playerBioPanel.selectFirst("div.row").select("div.row");

            Element firstRow = rows.get(1);
            country = firstRow.selectFirst("a.country").text();
            countryFlagUrl = firstRow.selectFirst("img.flag").attr("src").substring(9);

            Element secondRow = rows.get(2);
            discipline = secondRow.selectFirst("a").text();

            Element thirdRow = rows.get(3);
            Elements thirdRowDivs = thirdRow.select("div.col-md-6");

            Elements thirdRowFirstCol = thirdRowDivs.get(0).select("div");
            thirdRowFirstCol.select("label").remove();

            dob = thirdRowFirstCol.get(1).text();
            age = Integer.parseInt(thirdRowFirstCol.get(2).text());
            gender = thirdRowFirstCol.get(3).text();

            Elements thirdRowSecondCol = thirdRowDivs.get(1).select("div");

            for(Element div : thirdRowSecondCol.subList(1, thirdRowSecondCol.size())){
                String divText = div.text();
                if(divText.contains("Height")){
                    div.selectFirst("label").remove();
                    heightMeterAndFoot = div.text();
                }
                if(divText.contains("Place of birth")){
                    div.selectFirst("label").remove();
                    placeOfBirth = div.text();
                }
                if(divText.contains("Birth Country")){
                    div.selectFirst("label").remove();
                    birthCountry = div.text();
                }
                if(divText.contains("Place of residence")){
                    div.selectFirst("label").remove();
                    placeOfResidence = div.text();
                }
                if(divText.contains("Residence Country")){
                    div.selectFirst("label").remove();
                    residenceCountry = div.text();
                }

            }

            athlete = new Athlete(name, photoUrl, country, countryFlagUrl, discipline, dob, age, gender,
                    heightMeterAndFoot, placeOfBirth, birthCountry, placeOfResidence, residenceCountry);

            LOGGER.info(athlete.toString());
        } catch (IOException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return athlete;
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
