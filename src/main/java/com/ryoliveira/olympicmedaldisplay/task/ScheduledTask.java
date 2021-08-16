package com.ryoliveira.olympicmedaldisplay.task;

import com.ryoliveira.olympicmedaldisplay.service.*;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

@EnableScheduling
@Profile("databaseSetup")
@Service
public class ScheduledTask {

    private final DataScrapeService dataScrapeService;

    private final String PATH = "src\\main\\resources\\drivers\\chromedriver_92\\chromedriver.exe";
    private final String DRIVER = "webdriver.chrome.driver";

    public ScheduledTask(DataScrapeService dataScrapeService){
        this.dataScrapeService = dataScrapeService;
    }

    @Scheduled(initialDelay=0, fixedDelay=Long.MAX_VALUE)
    public void populateAthleteDatabase(){
        this.dataScrapeService.scrapeAthletes();
    }

    @Scheduled(initialDelay=0, fixedDelay=Long.MAX_VALUE)
    public void setChromeDriverPath(){
        System.setProperty(DRIVER, PATH);
    }


}
