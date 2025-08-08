package com.example.volt.Helper;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VisibilityHelper {
    private WebDriver driver;
    private Logger log = LoggerHelper.getLogger(VisibilityHelper.class);
    private WebDriverWait wait;

    public VisibilityHelper(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(16));
    }

    public boolean isPresent(WebElement element, String name) {
        Duration originalTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            var isDisplayed = element.isDisplayed();
            log.debug(name + " is present and " + (isDisplayed ? "displayed" : "hidden"));
            return isDisplayed;
        } catch (Exception e) {
            log.debug(name + " is not present");
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(originalTimeout);
        }
    }

    public boolean isPresentAndVisible(WebElement element, String name, int implicitTimeout) {
        Duration originalTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitTimeout));
            wait.until(ExpectedConditions.visibilityOf(element));
            boolean isDisplayed = element.isDisplayed();
            log.debug(
                    name + " is present and visible -> " + (isDisplayed ? "isDisplayed(true)" : "isDisplayed(false)"));
            return isDisplayed;
        } catch (NoSuchElementException e) {
            log.debug(name + " is not present");
            return false;
        } catch (TimeoutException e) {
            log.debug(name + " is present but still hidden after " + 16 + " seconds");
            return false;
        } catch (Exception e) {
            log.debug("An unexpected error occurred while checking for the visibility of " + name);
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(originalTimeout);
        }
    }
}
