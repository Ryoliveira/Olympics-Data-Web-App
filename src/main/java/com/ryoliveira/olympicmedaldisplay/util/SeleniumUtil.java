package com.ryoliveira.olympicmedaldisplay.util;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.*;

import java.util.*;


public class SeleniumUtil {

    private WebDriver chromeDriver;

    public SeleniumUtil(){
        ChromeOptions options = new ChromeOptions().setHeadless(true);
        chromeDriver = new ChromeDriver(options);
    }


    public List<String> renderPage(String url){
        List<String> tables = new ArrayList<>();
        chromeDriver.get(url);
        WebElement nextButton = chromeDriver.findElement(By.id("entries-table_next"));;
        JavascriptExecutor ex = (JavascriptExecutor) chromeDriver;
        do{
            tables.add(chromeDriver.getPageSource());
            ex.executeScript("arguments[0].click();", nextButton);
            nextButton = chromeDriver.findElement(By.id("entries-table_next"));
            System.out.println(tables.size());
            //Todo: loop doesnt stop at condition, fix this!!!
        }while(nextButton.isEnabled() && nextButton.isDisplayed());
        chromeDriver.quit();
        return tables;
    }

}
