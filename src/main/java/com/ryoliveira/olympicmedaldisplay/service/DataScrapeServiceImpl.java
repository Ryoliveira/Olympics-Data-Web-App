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
import java.util.stream.*;

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
            for (Element sportItem : sportListItems) {
                String sportName = sportItem.selectFirst("h2").text().replaceAll("\\/ ", "-");

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
                String iconUrl = BASE_URL + cssSelectorSub.substring(cssSelectorSub.indexOf("(") + 1);
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
            countries.add(new Country("All Countries", null, null));
            for (Element countryItem : countryListItems) {
                String countryProfileHref = countryItem.selectFirst("a").attr("href");
                String profileKey = "profile-";
                int startIndexSlice = countryProfileHref.indexOf(profileKey) + profileKey.length();
                int endIndexSlice = countryProfileHref.indexOf(".htm");
                String countryProfileStrId = countryProfileHref.substring(startIndexSlice, endIndexSlice);
                String countryFlagUrl = BASE_URL + "/tokyo-2020/olympic-games/" + countryItem.selectFirst("img")
                        .attr("src")
                        .substring(9);
                String countryName = countryItem.text();
                Country country = new Country(countryName, countryFlagUrl, countryProfileStrId);
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

        CountryInformation countryInformation = new CountryInformation();

        try {
            Document doc = Jsoup.connect(profileUrl).get();

            Element teamInfoDiv = doc.selectFirst("h1.NoSport");

            int countryNameStartIndex = teamInfoDiv.text().indexOf("Team") + "Team".length();
            int countryNameEndIndex = teamInfoDiv.text().indexOf("-");
            String countryName = teamInfoDiv.text().substring(countryNameStartIndex, countryNameEndIndex).trim();
            String countryFlagUrl = "https://olympics.com/tokyo-2020/olympic-games/" + teamInfoDiv.selectFirst("img").attr("src").substring(9);
            countryInformation.setCountryName(countryName);
            countryInformation.setCountryFlagUrl(countryFlagUrl);
            countryInformation.setProfilePageStrId(country);

            Element panelBio = doc.selectFirst("div.panel-bio");
            Elements panelChildren = panelBio.children();
            extractAndSetCurrentMedalsTable(panelBio, countryInformation);
            extractAndSetCountryHighlightsInfo(panelBio, countryInformation);
            extractAndSetCountryAdditionalInfo(panelBio, countryInformation);
            extractAndSetCountryFlagbearerInfo(panelBio, panelChildren, countryInformation);
            extractAndSetCountryParticipationInfo(panelBio, panelChildren, countryInformation);
            extractAndSetCountryParticipationTables(panelBio, countryInformation);

            List<InfoSnippet> anthemInfo = extractGeneralInfo("Anthem", panelBio, panelChildren);
            List<InfoSnippet> membershipInfo = extractGeneralInfo("Membership", panelBio, panelChildren);
            List<InfoSnippet> officialsInfo = extractGeneralInfo("Officials", panelBio, panelChildren);
            countryInformation.setAnthemInfo(anthemInfo);
            countryInformation.setMembershipInfo(membershipInfo);
            countryInformation.setOfficialsInfo(officialsInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return countryInformation;
    }

    private void extractAndSetCurrentMedalsTable(Element panelBio, CountryInformation countryInformation) {
        List<String> subStandingCategories = new ArrayList<>();
        List<SubMedalStanding> medalsByDivision = new ArrayList<>();

        Element standingTable = panelBio.selectFirst("table#medal-standing-table");
        Elements columnCategories = standingTable.selectFirst("tr").select("th");
        for (Element category : columnCategories.subList(1, columnCategories.size() - 1)) {
            subStandingCategories.add(category.text());
        }

        Element table;
        if ((table = standingTable.selectFirst("tbody")) != null) {
            Elements tableData = table.select("td");

            int rank = Integer.parseInt(tableData.get(0).text());


            List<Integer> medalCounts = new ArrayList<>();


            //Correctly parses and extracts medals from each division (Men, Women, Mixed, Open)
            for (int i = 1, j = 0; i < tableData.size(); i++) {
                if (j > 1 && j % 4 == 0) {
                    String category = subStandingCategories.get((j - 1) / 4);
                    medalsByDivision.add(new SubMedalStanding(category,
                            medalCounts.get(0), medalCounts.get(1), medalCounts.get(2), medalCounts.get(3)));
                    medalCounts.clear();
                }
                int medalCount = Integer.parseInt(tableData.get(i).text());
                medalCounts.add(medalCount);
                j++;
            }

            int totalRank = Integer.parseInt(tableData.get(tableData.size() - 1).text());

            countryInformation.setRank(rank);
            countryInformation.setMedalsByDivision(medalsByDivision);
            countryInformation.setTotalRank(totalRank);
        }
    }

    private void extractAndSetCountryHighlightsInfo(Element panelBio, CountryInformation countryInformation) {
        Element highlightPortion = panelBio.selectFirst("label:contains(Highlights)").parent();
        String highlightLabel = highlightPortion.selectFirst("label").text();
        highlightPortion.selectFirst("label").remove();
        List<String> highlightContent = new ArrayList<>(Arrays.asList(highlightPortion.html()
                .replaceAll("<\\/*p>", "").split("<br>")));
        highlightContent.removeIf(x -> x.matches("\\s*"));
        String content = highlightContent.stream().collect(Collectors.joining("</br></br>"));
        countryInformation.setHighlights(new InfoSnippet(highlightLabel, content));
    }

    private void extractAndSetCountryAdditionalInfo(Element panelBio, CountryInformation countryInformation) {
        List<InfoSnippet> linkSnippets = new ArrayList<>();

        try {

            Element additionalInfo = panelBio.selectFirst("label:contains(Additional)").parent();
            Elements linkLabels = additionalInfo.select("b");
            Elements links = additionalInfo.select("a");
            for (int i = 0; i < links.size(); i++) {
                String linkLabel = linkLabels.get(i).text();
                String linkUrl = links.get(i).attr("href");
                linkSnippets.add(new InfoSnippet(linkLabel, linkUrl));
            }
        } catch (NullPointerException e) {
            LOGGER.error("Additional Links Not Provided");
        }
        countryInformation.setLinks(linkSnippets);
    }

    private void extractAndSetCountryFlagbearerInfo(Element panelBio, Elements panelChildren, CountryInformation countryInformation) {
        List<Athlete> flagbearers = new ArrayList<>();
        Element flagbearerBanner = panelBio.selectFirst("a:contains(Flagbearers)").parent();
        int flagbearerIndex = panelChildren.indexOf(flagbearerBanner) + 1;
        Element flagBearerInfoDiv;
        while ((flagBearerInfoDiv = panelChildren.get(flagbearerIndex)).tagName().equals("div")) {
            String bearerName = flagBearerInfoDiv.selectFirst("span.d-none").text();
            Athlete athlete = this.athleteService.getAthleteByName(bearerName);
            flagbearers.add(athlete);
            flagbearerIndex++;
        }
        countryInformation.setFlagbearerInfo(flagbearers);
    }

    private void extractAndSetCountryParticipationInfo(Element panelBio, Elements panelChildren, CountryInformation countryInformation) {
        List<InfoSnippet> participationInfoSnippets = new ArrayList<>();

        Element participationBanner = panelBio.selectFirst("a:contains(Participation)").parent();
        int participationInfoStartIndex = panelChildren.indexOf(participationBanner) + 1;
        int participationInfoEndIndex = participationInfoStartIndex + 2;
        for (int i = participationInfoStartIndex; i < participationInfoEndIndex; i++) {
            Element participationInfoDiv = panelChildren.get(i);
            Element label = participationInfoDiv.selectFirst("label");
            String labelText = label.text();
            label.remove();
            String infoText = participationInfoDiv.text();
            participationInfoSnippets.add(new InfoSnippet(labelText, infoText));
        }
        countryInformation.setParticipationInfo(participationInfoSnippets);
    }

    private void extractAndSetCountryParticipationTables(Element panelBio, CountryInformation countryInformation) {
        List<List<SubMedalStanding>> medalTables = new ArrayList<>();

        try {
            Element participationTableDiv = panelBio.selectFirst("div.divBio");
            Elements participationTables = participationTableDiv.select("table");
            for (Element pTable : participationTables) {
                List<SubMedalStanding> tableRows = new ArrayList<>();
                Element tableBody = pTable.selectFirst("tbody");
                for (Element row : tableBody.children()) {
                    Elements cols = row.children();
                    String category = cols.get(0).text();
                    int gold = Integer.parseInt(cols.get(1).text());
                    int silver = Integer.parseInt(cols.get(2).text());
                    int bronze = Integer.parseInt(cols.get(3).text());
                    int total = Integer.parseInt(cols.get(4).text());
                    tableRows.add(new SubMedalStanding(category, gold, silver, bronze, total));
                }
                medalTables.add(tableRows);
            }
            countryInformation.setMedalsBySport(medalTables.get(0));
            countryInformation.setMedalsByYear(medalTables.get(1));
        } catch (NullPointerException e) {
            LOGGER.error("No Participation Tables Found");
        }
    }

    private List<InfoSnippet> extractGeneralInfo(String bannerName, Element panelBio, Elements panelChildren) {
        List<InfoSnippet> infoSnippets = new ArrayList<>();

        Element banner = panelBio.selectFirst("a:contains(" + bannerName + ")").parent();
        int infoStartIndex = panelChildren.indexOf(banner) + 1;
        int infoEndIndex = infoStartIndex + 3;
        for (int i = infoStartIndex; i < infoEndIndex; i++) {
            try {
                Element infoDiv = panelChildren.get(i);
                Element labelElement = infoDiv.selectFirst("label");
                String infoLabel = labelElement.text();
                labelElement.remove();
                String info = infoDiv.text();
                infoSnippets.add(new InfoSnippet(infoLabel, info));
            } catch (NullPointerException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return infoSnippets;
    }

    @Override
    public SportInformation getSportInformation(String sport) {
        sport = sport.replaceAll("\\s+", "-");
        String sportPagePath = "/tokyo-2020/en/sports/";
        String sportsPageUrl = BASE_URL + sportPagePath + sport;

        String sportTitle = sport;
        List<Tab> sportTabs = new ArrayList<>();
        List<Article> articles = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(sportsPageUrl).get();
            sportTitle = doc.selectFirst("h1.tk-details-sport__title").text();
            Elements tabs = doc.select("a.tk-article__tabs-item--title");

            LOGGER.info("Tabs Size: " + tabs.size());

            if (tabs.size() == 0) {
                articles = parseArticlePage(doc);
                sportTabs.add(new Tab(null, articles));
            } else {
                for (Element tab : tabs) {
                    String tabUrl = tab.attr("data-href");
                    LOGGER.info("Tab URL: " + tabUrl);
                    Document tabDoc = Jsoup.connect(tabUrl).get();
                    String tabTitle = tab.text();
                    articles = parseArticlePage(tabDoc);
                    sportTabs.add(new Tab(tabTitle, articles));
                }
            }

            LOGGER.info(articles.toString());
        } catch (IOException e) {
            LOGGER.error("getSportInformation(): " + e.getMessage());
        }
        return new SportInformation(sportTitle, sportTabs);
    }

    private List<Article> parseArticlePage(Document articlePage) {
        List<Article> articles = new ArrayList<>();

        Element mainArticleBody = articlePage.selectFirst("div.tk-article__body");

        Element overview = mainArticleBody.selectFirst("h2:contains(Overview)");

        //Some sports have a H3 tag for their article headers rather than the more common H2
        Element eventProgrammeH2 = mainArticleBody.selectFirst("h2:contains(Event)");
        Element eventProgramme = (eventProgrammeH2 == null) ? mainArticleBody.selectFirst("h3:contains(Event)") : eventProgrammeH2;
        Element essenceOfTheSport = mainArticleBody.selectFirst("h2:contains(Essence of the sport)");

        try {
            articles.add(extractArticleContents(overview, "p"));
            articles.add(extractArticleContents(eventProgramme, "li"));
            articles.add(extractArticleContents(essenceOfTheSport, "p"));

        } catch (NullPointerException e) {
            LOGGER.error("parseArticlePage(): " + e.getMessage());
        }
        return articles;
    }

    private Article extractArticleContents(Element articleHeader, String contentTag) {
        String title = articleHeader.text();
        List<String> contents = new ArrayList<>();

        Element parent = articleHeader.parent();

        for (Element sentence : parent.select(contentTag)) {
            contents.add(sentence.text());
        }
        Article article = new Article(title, contents);
        LOGGER.info(article.toString());
        return article;
    }

    private Athlete createAthlete(String athletePageUrl) {
        String url = String.format("%s/tokyo-2020/olympic-games/%s", BASE_URL, athletePageUrl);
        String fullPath = String.format("%s/tokyo-2020/olympic-games/", BASE_URL);

        Athlete athlete = new Athlete();

        try {
            Document doc = Jsoup.connect(url).get();
            extractAndSetAthleteName(athlete, doc);

            Element playerBioPanel = doc.selectFirst("div.panel-bio");
            extractAndSetAthletePhoto(fullPath, athlete, playerBioPanel);

            Elements rows = playerBioPanel.selectFirst("div.row").select("div.row");
            extractAndSetAtheleteCountryInformation(fullPath, athlete, rows);
            extractAndSetAthleteDiscipline(athlete, rows);
            extractAndSetAtleteBasicInformation(athlete, rows);

            Element additionalInfo = playerBioPanel.selectFirst("[name=\"bio\"]");
            extractAndSetAthleteAdditionalInfo(athlete, additionalInfo);

        } catch (IOException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return athlete;
    }

    private void extractAndSetAthleteName(Athlete athlete, Document doc) {
        String name;
        name = doc.selectFirst("h1").text();
        athlete.setName(name);
    }

    private void extractAndSetAthletePhoto(String fullPath, Athlete athlete, Element playerBioPanel) {
        String photoUrl;
        photoUrl = fullPath + playerBioPanel.selectFirst("img").attr("src").substring(9);
        athlete.setPhotoUrl(photoUrl);
    }

    private void extractAndSetAtleteBasicInformation(Athlete athlete, Elements rows) {
        String placeOfBirth;
        String residenceCountry;
        String dob;
        String gender;
        String heightMeterAndFoot;
        String birthCountry;
        int age;
        String placeOfResidence;
        Element thirdRow = rows.get(3);
        Elements thirdRowDivs = thirdRow.select("div.col-md-6");

        Elements thirdRowFirstCol = thirdRowDivs.get(0).select("div");
        thirdRowFirstCol.select("label").remove();

        dob = thirdRowFirstCol.get(1).text();
        athlete.setDob(dob);
        age = Integer.parseInt(thirdRowFirstCol.get(2).text());
        athlete.setAge(age);
        gender = thirdRowFirstCol.get(3).text();
        athlete.setGender(gender);

        Elements thirdRowSecondCol = thirdRowDivs.get(1).select("div");

        for (Element div : thirdRowSecondCol.subList(1, thirdRowSecondCol.size())) {
            String divText = div.text();
            if (divText.contains("Height")) {
                div.selectFirst("label").remove();
                heightMeterAndFoot = div.text();
                athlete.setHeight(heightMeterAndFoot);
            }
            if (divText.contains("Place of birth")) {
                div.selectFirst("label").remove();
                placeOfBirth = div.text();
                athlete.setPlaceOfBirth(placeOfBirth);
            }
            if (divText.contains("Birth Country")) {
                div.selectFirst("label").remove();
                birthCountry = div.text();
                athlete.setBirthCountry(birthCountry);
            }
            if (divText.contains("Place of residence")) {
                div.selectFirst("label").remove();
                placeOfResidence = div.text();
                athlete.setPlaceOfResidence(placeOfResidence);

            }
            if (divText.contains("Residence Country")) {
                div.selectFirst("label").remove();
                residenceCountry = div.text();
                athlete.setResidenceCountry(residenceCountry);
            }

        }
    }

    private void extractAndSetAthleteDiscipline(Athlete athlete, Elements rows) {
        String discipline;
        Element secondRow = rows.get(2);
        discipline = secondRow.selectFirst("a").text().replace("/", "-");
        athlete.setDiscipline(discipline);
    }

    private void extractAndSetAtheleteCountryInformation(String fullPath, Athlete athlete, Elements rows) {
        String country;
        String countryFlagUrl;
        Element firstRow = rows.get(1);
        country = firstRow.selectFirst("a.country").text();
        athlete.setCountry(country);
        countryFlagUrl = fullPath + firstRow.selectFirst("img.flag").attr("src").substring(9);
        athlete.setCountryFlagUrl(countryFlagUrl);
    }

    private void extractAndSetAthleteAdditionalInfo(Athlete athlete, Element additionalInfo) {
        List<InfoSnippet> athleteInfoSnippets = new ArrayList<>();
        for(Element child : additionalInfo.select("div.form-group")){
            if(child.select("table").size() == 0){
                Element label = child.selectFirst("label");
                String labelText = label.text();
                label.remove();
                String info = child.selectFirst("div").text().replaceAll(": ", "");
                athleteInfoSnippets.add(new InfoSnippet(labelText, info));
            }
            if(child.select("a").size() > 0){
                break;
            }
        }

        athlete.setAdditionalInfo(athleteInfoSnippets);
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
