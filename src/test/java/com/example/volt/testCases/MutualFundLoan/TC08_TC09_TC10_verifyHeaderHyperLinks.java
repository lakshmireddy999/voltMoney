package com.example.volt.testCases.MutualFundLoan;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.volt.PageObjects.MutualFundLoan.MFLoanPage;
import com.example.volt.testCases.BaseTest;

public class TC08_TC09_TC10_verifyHeaderHyperLinks extends BaseTest {
    MFLoanPage mfLoanPage;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        mfLoanPage = new MFLoanPage(driver);
    }

    @Test(description = "This test url redirection to various header elements", groups={"regression"})
    public void validateHeaderHyperLinks() {
        startDriver();
        mfLoanPage.clickContactUs();
        Assert.assertEquals(driver.getCurrentUrl(), "https://voltmoney.in/contact");
        startDriver();

        mfLoanPage.clickAboutUs();
        Assert.assertEquals(driver.getCurrentUrl(), "https://voltmoney.in/about");
        startDriver();

        mfLoanPage.clickSignIn();
        List<String> windowList = getWindowHandles();
        driver.switchTo().window(windowList.get(1));
        Assert.assertEquals(driver.getCurrentUrl(), "https://app.voltmoney.in/?startnew=true");
    }
}
