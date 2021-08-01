package com.ryoliveira.olympicmedaldisplay.util;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.slf4j.*;

import java.util.*;


public class SeleniumUtil {

    Logger LOGGER = LoggerFactory.getLogger(SeleniumUtil.class);

    private WebDriver chromeDriver;

    public SeleniumUtil(){
        ChromeOptions options = new ChromeOptions().setHeadless(true);
        chromeDriver = new ChromeDriver(options);
    }


    public List<String> renderPages(String url){
        List<String> pages = new ArrayList<>();
        chromeDriver.get(url);
        WebElement nextButton = chromeDriver.findElement(By.id("entries-table_next"));;
        JavascriptExecutor ex = (JavascriptExecutor) chromeDriver;

        List<WebElement> paginationList = chromeDriver.findElement(By.className("pagination")).findElements(By.tagName("li"));

        int totalPageNum = Integer.parseInt(paginationList.get(paginationList.size()-2).getText());

        LOGGER.info("Total Pages: " + totalPageNum);

        for(int i = 0; i < 5; i++){
            pages.add(chromeDriver.getPageSource());
            ex.executeScript("arguments[0].click();", nextButton);
            nextButton = chromeDriver.findElement(By.id("entries-table_next"));
        }
        chromeDriver.quit();
        return pages;
    }

}
