package com.example.volt.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.apache.log4j.Logger;

import com.example.volt.Helper.ActionsHelper;
import com.example.volt.Helper.VisibilityHelper;
import com.example.volt.Helper.WaitHelper;

public class BasePageObject {
    public Logger logger;
    public WebDriver driver;
    public WaitHelper waitHelper;
    public VisibilityHelper visibilityHelper;
    public ActionsHelper actionsHelper;

    public BasePageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        logger = Logger.getLogger(this.getClass());
        visibilityHelper = new VisibilityHelper(driver);
        actionsHelper = new ActionsHelper(driver);
        waitHelper = new WaitHelper(driver);
        waitHelper.pageLoadTime(30);
        logger.debug(this.getClass().getName() + " Page Object Initialized");
    }
}
