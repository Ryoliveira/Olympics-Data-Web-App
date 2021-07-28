package com.ryoliveira.olympicmedaldisplay.util;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class SeleniumUtil {


    public String renderPage(String url){
        ChromeOptions options = new ChromeOptions().setHeadless(true);
        WebDriver chromeDriver = new ChromeDriver(options);
        chromeDriver.get(url);
        String page = chromeDriver.getPageSource();
        chromeDriver.quit();
        return page;
    }

}
