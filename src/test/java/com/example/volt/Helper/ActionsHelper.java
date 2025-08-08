package com.example.volt.Helper;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionsHelper {
    private WebDriver driver;
    private Logger log = LoggerHelper.getLogger(ActionsHelper.class);
    private FluentWait<WebDriver> fluentWait;
    private WebDriverWait wait;
    private static Set<String> elements = new HashSet<>();

    public ActionsHelper(WebDriver driver) {
        this.driver = driver;
        fluentWait = new FluentWait<WebDriver>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(16));
        fluentWait.ignoring(Exception.class);
        wait = new WebDriverWait(driver, Duration.ofSeconds(16));
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' })", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
    }

    public void setText(WebElement element, String stringToType) {
        var originalTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
            log.debug(getElementString(element) + " is visible now");
            element.click();
            element.clear();
            element.sendKeys(stringToType);
            wait.until(ExpectedConditions.textToBePresentInElementValue(element, stringToType));
            log.debug(stringToType + " was typed successfully on " + getElementString(element));
        } finally {
            driver.manage().timeouts().implicitlyWait(originalTimeout);
        }
    }

    public void clearTextField(WebElement element) {
        var originalTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        try {
            Actions action = new Actions(driver);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
            action.keyDown(Keys.CONTROL).sendKeys("a").sendKeys(element, Keys.BACK_SPACE).keyUp(Keys.CONTROL).build()
                    .perform();
            action.click(element).sendKeys(Keys.chord(Keys.BACK_SPACE).repeat(100)).build().perform();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value='';", element);
            log.debug(element + " text cleared successfully");
        } finally {
            driver.manage().timeouts().implicitlyWait(originalTimeout);
        }
    }

    public void hoverOver(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    private String getElementString(WebElement element) {
        var elementString = element.toString();
        var start = elementString.indexOf(" ->") > 0 ? elementString.indexOf("->")
                : elementString.indexOf("By.") > 0 ? elementString.indexOf("By.") : 0;
        return "WebElement " + elementString.substring(start, elementString.length() - 1);
    }
}
