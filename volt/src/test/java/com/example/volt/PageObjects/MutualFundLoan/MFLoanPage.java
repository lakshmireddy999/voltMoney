package com.example.volt.PageObjects.MutualFundLoan;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.example.volt.PageObjects.BasePageObject;

public class MFLoanPage extends BasePageObject {

    public MFLoanPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='header_benefitsContainer__RU8nQ']")
    WebElement btnBenefits;

    @FindBy(xpath = "//div[@class='header_headerLinksContainer__vC_ed'][text()='FAQs']")
    WebElement btnFaqs;

    @FindBy(xpath = "//div[@class='header_integrateWithUsContainer__7zqZs']//div[text()='Resources']")
    WebElement btnResources;

    @FindBy(xpath = "//img[@alt='Volt money logo']")
    WebElement btnVoltHome;

    @FindBy(xpath = "//div[text()='Interest calculator']")
    WebElement btnInterestCalculator;

    @FindBy(xpath = "//div[text()='Capital gain statement']")
    WebElement btnCapitalGainStatement;

    @FindBy(xpath = "//div[@class='button_buttonPrimaryLarge__zgNyF']")
    WebElement btnCheckEligibility;

    @FindBy(xpath = "//div[@class='header_integrateWithUsContainer__7zqZs']//div[text()='Partner with us']")
    WebElement btnPartnerWithUs;

    @FindBy(xpath = "//div[text()='For Mutual fund distributors']")
    WebElement btnForMFDistributors;

    public void clickVoltHome(){
        click(btnVoltHome);
    }

    public void clickInterestCaluculator(){
        actionsHelper.hoverOver(btnResources);
        click(btnInterestCalculator);
    }

    public void clickCapitalGainStatement(){
        actionsHelper.hoverOver(btnResources);
        click(btnCapitalGainStatement);
    }

    public void clickCheckEligibility(){
        click(btnCheckEligibility);
    }

    public void clickMFDistributors(){
        actionsHelper.hoverOver(btnPartnerWithUs);
        click(btnForMFDistributors);
    }
}
