package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;
import com.ryoliveira.olympicmedaldisplay.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Service
public class DataScrapeServiceImpl implements DataScrapeService {

    private final String BASE_URL = "https://olympics.com";
    private final AthleteService athleteService;
    Logger LOGGER = LoggerFactory.getLogger(DataScrapeServiceImpl.class);

    public DataScrapeServiceImpl(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    public TeamList getStandings(String sport) {
        sport = sport.replaceAll("\\s+", "-");
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

        List<Sport> sportsList = new ArrayList<>();
        sportsList.add(new Sport("All Sports", null));

        try {
            Document doc = Jsoup.connect(sportsUrl).get();
            String cssUrl = BASE_URL + doc.select("link[rel=stylesheet]").get(1).attr("href");
            String cssDocString = Jsoup.connect(cssUrl).get().toString();

            Elements sportListItems = doc.select("li.tk-disciplines__item");
            for(Element sportItem : sportListItems){
                String sportName = sportItem.selectFirst("h2").text();

                // Selects the name of the classname in css file where icon svg url is located
                String cssIconClassName = sportItem.selectFirst("div").attr("class").split("\\s")[1];
                // Selects the starting index icon classname in cssfile
                int startIndex = cssDocString.indexOf(cssIconClassName);
                // Find the index near the end of the icon path
                String sportIconStr = cssIconClassName.substring(3) + ".svg";
                // Add the length of the previous string to find index of end of path
                int endIndex = cssDocString.indexOf(sportIconStr) + sportIconStr.length();
                String cssSelectorSub = cssDocString.substring(startIndex, endIndex);
                // Get substring which only includes relative path to icon and append it to base url to create absolute path
                String iconUrl = BASE_URL + cssSelectorSub.substring(cssSelectorSub.indexOf("(")+1);
                sportsList.add(new Sport(sportName, iconUrl));
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return new SportsList(sportsList);
    }

    @Override
    public void scrapeAthletes() {
        String athleteListUrl = String.format("%s/tokyo-2020/olympic-games/en/results/all-sports/athletes.htm", BASE_URL);

        List<String> htmlPages = new SeleniumUtil().renderPages(athleteListUrl);
        for (String page : htmlPages) {
            Document doc = Jsoup.parse(page);
            Elements playerTags = doc.select("div.playerTag");
            for (Element playerTag : playerTags) {
                Element athletePageLink = playerTag.selectFirst("a[href]");
                Athlete createdAthlete = createAthlete(athletePageLink.attr("href").substring(9));
                athleteService.saveAthlete(createdAthlete);
            }
            if (playerTags.size() == 0) {
                LOGGER.info("No Tags!!!");
            }
        }
    }

    @Override
    public CountryList getCountryList() {
        List<Country> countries = new ArrayList<>();
        String countryPath = "/tokyo-2020/olympic-games/en/results/all-sports/nocs-list.htm";
        String url = BASE_URL + countryPath;

        try {
            Document doc = Jsoup.connect(url).get();
            Elements countryListItems = doc.selectFirst("ul.list-unstyled").select("li");
            countries.add(new Country("All Countries", null));
            for(Element countryItem : countryListItems){
                String countryFlagUrl = BASE_URL + "/tokyo-2020/olympic-games/" + countryItem.selectFirst("img")
                                                                                         .attr("src")
                                                                                         .substring(9);
                String countryName = countryItem.text();
                Country country = new Country(countryName, countryFlagUrl);
                countries.add(country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CountryList(countries);
    }

    @Override
    public CountryInformation scrapeCountryInformation(String country) {
        String profileUrl = BASE_URL + "/tokyo-2020/olympic-games/en/results/all-sports/noc-profile-" + country + ".htm";


        try{
            Document doc = Jsoup.connect(profileUrl).get();

            Element panelBio = doc.selectFirst("div.panel-bio");

            LOGGER.info(panelBio.toString());




        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    //Todo: fix scraping with sports(Baseball/Softball && Cycling Road)
    @Override
    public SportInformation getSportInformation(String sport) {
        sport = sport.replaceAll("\\s+", "-");
        String sportPagePath = "/tokyo-2020/en/sports/";
        String sportsPageUrl = BASE_URL + sportPagePath + sport;

        String sportTitle = sport;
        List<Tab> sportTabs = new ArrayList<>();
        List<Article> articles = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(sportsPageUrl).get();
            sportTitle = doc.selectFirst("h1.tk-details-sport__title").text();
            Elements tabs = doc.select("a.tk-article__tabs-item--title");

            LOGGER.info("Tabs Size: " + tabs.size());

            if(tabs.size() == 0){
                articles = parseArticlePage(doc);
                sportTabs.add(new Tab(null, articles));
            }else{
                for(Element tab : tabs){
                    String tabUrl = tab.attr("data-href");
                    LOGGER.info("Tab URL: " + tabUrl);
                    Document tabDoc = Jsoup.connect(tabUrl).get();
                    String tabTitle = tab.text();
                    articles = parseArticlePage(tabDoc);
                    sportTabs.add(new Tab(tabTitle, articles));
                }
            }

            LOGGER.info(articles.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new SportInformation(sportTitle, sportTabs);
    }

    private List<Article> parseArticlePage(Document articlePage){
        List<Article> articles = new ArrayList<>();

        Element mainArticleBody = articlePage.selectFirst("div.tk-article__body");

        Element overview = mainArticleBody.selectFirst("h2:contains(Overview)");

        //Some sports have a H3 tag for their article headers rather than the more common H2
        Element eventProgrammeH2 = mainArticleBody.selectFirst("h2:contains(Event)");
        Element eventProgramme = (eventProgrammeH2 == null) ? mainArticleBody.selectFirst("h3:contains(Event)") : eventProgrammeH2;
        Element essenceOfTheSport = mainArticleBody.selectFirst("h2:contains(Essence of the sport)");

        articles.add(extractArticleContents(overview, "p"));
        articles.add(extractArticleContents(eventProgramme, "li"));
        articles.add(extractArticleContents(essenceOfTheSport, "p"));

        return articles;
    }

    private Article extractArticleContents(Element articleHeader, String contentTag){
        String title = articleHeader.text();
        List<String> contents = new ArrayList<>();

        Element parent = articleHeader.parent();

        for(Element sentence : parent.select(contentTag)){
            contents.add(sentence.text());
        }
        Article article = new Article(title, contents);
        LOGGER.info(article.toString());
        return article;
    }

    private Athlete createAthlete(String athletePageUrl) {
        String url = String.format("%s/tokyo-2020/olympic-games/%s", BASE_URL, athletePageUrl);
        String fullPath = String.format("%s/tokyo-2020/olympic-games/", BASE_URL);

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

            photoUrl = fullPath + playerBioPanel.selectFirst("img").attr("src").substring(9);

            Elements rows = playerBioPanel.selectFirst("div.row").select("div.row");

            Element firstRow = rows.get(1);
            country = firstRow.selectFirst("a.country").text();
            countryFlagUrl = fullPath + firstRow.selectFirst("img.flag").attr("src").substring(9);

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

            for (Element div : thirdRowSecondCol.subList(1, thirdRowSecondCol.size())) {
                String divText = div.text();
                if (divText.contains("Height")) {
                    div.selectFirst("label").remove();
                    heightMeterAndFoot = div.text();
                }
                if (divText.contains("Place of birth")) {
                    div.selectFirst("label").remove();
                    placeOfBirth = div.text();
                }
                if (divText.contains("Birth Country")) {
                    div.selectFirst("label").remove();
                    birthCountry = div.text();
                }
                if (divText.contains("Place of residence")) {
                    div.selectFirst("label").remove();
                    placeOfResidence = div.text();
                }
                if (divText.contains("Residence Country")) {
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
