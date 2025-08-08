package com.example.volt.Helper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.apache.log4j.Logger;

public class WaitHelper {
    private WebDriver driver;
    private Logger log = LoggerHelper.getLogger(WaitHelper.class);
    public FluentWait<WebDriver> fluentWait;

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        fluentWait = new FluentWait<WebDriver>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(16));
        fluentWait.ignoring(Exception.class);
    }

    private String getElementString(WebElement element) {
        var elementString = element.toString();
        var start = elementString.indexOf(" ->") > 0 ? elementString.indexOf("->")
                : elementString.indexOf("By.") > 0 ? elementString.indexOf("By.") : 0;
        return "WebElement " + elementString.substring(start, elementString.length() - 1);
    }

    public void pageLoadTime(long timeOutInSeconds) {
        log.debug("waiting for page to load for : " + timeOutInSeconds + " seconds");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeOutInSeconds));
        log.debug("page is loaded");
    }

    public void waitForElementClickable(WebElement element) {
        var originalTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            log.debug("waiting for :" + getElementString(element) + " for :" + 16
                    + " seconds");
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
            log.debug("element is clickable now");
        } finally {
            driver.manage().timeouts().implicitlyWait(originalTimeout);
        }
    }

    public void waitForElementVisible(int timeout, WebElement... elements) {
        var originalTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            List<ExpectedCondition<WebElement>> conditions = new ArrayList<>();
            for (WebElement element : elements) {
                conditions.add(ExpectedConditions.visibilityOf(element));
            }
            FluentWait<WebDriver> customWait = new FluentWait<>(driver);
            customWait.withTimeout(Duration.ofSeconds(timeout));
            customWait.ignoring(Exception.class);
            customWait.until(ExpectedConditions.or(conditions.toArray(new ExpectedCondition[0])));
        } finally {
            driver.manage().timeouts().implicitlyWait(originalTimeout);
        }
    }
}
