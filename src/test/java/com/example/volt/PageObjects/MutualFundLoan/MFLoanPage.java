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

    @FindBy(xpath = "//div[@class='header_headerLinksContainer__vC_ed'][text()='Contact us']")
    WebElement btnContactUs;

    @FindBy(xpath = "//div[@class='header_headerLinksContainer__vC_ed'][text()='About us']")
    WebElement btnAboutUs;

    @FindBy(xpath = "//div[@class='button_buttonOutlineTransparentLarge___Dr9n']/div[text()='Sign in']")
    WebElement btnSignIn;

    @FindBy(xpath = "//input[@placeholder='Enter mobile number']")
    WebElement txtMobile;

    @FindBy(xpath = "//input[@placeholder='Enter PAN']")
    WebElement txtPan;

    @FindBy(xpath = "//div[text()='Enter a valid mobile number']")
    WebElement txtMobileError;

    @FindBy(xpath = "//div[text()='Enter a valid PAN']")
    WebElement txtPanError;

    @FindBy(xpath = "//div[@class='button_buttonContainerPrimary__ij4d0']")
    WebElement btnCheckEligibilityForFree;

    @FindBy(xpath = "//button/span[text()='Edit details']")
    WebElement btnEditDetails;

    @FindBy(xpath = "//span[text()='T&Cs']")
    WebElement hyperLinktermsandconditions;

    @FindBy(xpath = "//span[text()='Privacy Policy']")
    WebElement hyperLinkPrivacyPolicy;

    public void clickPrivacyPolicy(){
        click(hyperLinkPrivacyPolicy);
    }

    public void clickTermsAndConditions(){
        actionsHelper.scrollIntoView(hyperLinktermsandconditions);
        click(hyperLinktermsandconditions);
    }

    public void clickCheckEligibilityForFree() {
        click(btnCheckEligibilityForFree);
        waitHelper.waitForElementVisible(16, btnEditDetails);
    }

    public boolean verifyPanError() {
        return visibilityHelper.isPresent(txtPanError, "Enter Valid Pan");
    }

    public boolean verifyMobileError() {
        return visibilityHelper.isPresent(txtMobileError, "Enter a valis number");
    }

    public void setPanNumber(String pan) {
        setText(txtPan, pan);
    }

    public void clickTxtPan() {
        click(txtPan);
    }

    public String getPanNumber() {
        return txtPan.getDomAttribute("value");
    }

    public String getMobileNumber() {
        return txtMobile.getDomAttribute("value");
    }

    public void setMobileNumber(String mobile) {
        setText(txtMobile, mobile);
    }

    public void clickSignIn() {
        click(btnSignIn);
    }

    public void clickAboutUs() {
        click(btnAboutUs);
    }

    public void clickVoltHome() {
        click(btnVoltHome);
    }

    public void clickInterestCaluculator() {
        actionsHelper.hoverOver(btnResources);
        click(btnInterestCalculator);
    }

    public void clickCapitalGainStatement() {
        actionsHelper.hoverOver(btnResources);
        click(btnCapitalGainStatement);
    }

    public void clickCheckEligibility() {
        click(btnCheckEligibility);
    }

    public void clickMFDistributors() {
        actionsHelper.hoverOver(btnPartnerWithUs);
        click(btnForMFDistributors);
    }

    public void clickContactUs() {
        click(btnContactUs);
    }
}
