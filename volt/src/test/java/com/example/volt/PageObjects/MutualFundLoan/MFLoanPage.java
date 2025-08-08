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

    public void clickVoltHome(){
        click(btnVoltHome);
    }
}
