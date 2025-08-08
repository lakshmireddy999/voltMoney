package com.example.volt.testCases.MutualFundLoan;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.volt.PageObjects.MutualFundLoan.MFLoanPage;
import com.example.volt.testCases.BaseTest;

public class TC17_TC18_hyperLinks extends BaseTest {
    MFLoanPage mfLoanPage;
    List<String> windowHandles;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        mfLoanPage = new MFLoanPage(driver);
    }

    @Test(description = "this test t&c hyperlinks", groups = { "regression" })
    public void testHyperLinks() {
        startDriver();
        mfLoanPage.clickTermsAndConditions();
        windowHandles = getWindowHandles();
        driver.switchTo().window(windowHandles.get(1));
        Assert.assertEquals(driver.getCurrentUrl(), "https://voltmoney.in/terms?showHeader=false");
        driver.close();
        driver.switchTo().window(windowHandles.get(0));

        mfLoanPage.clickPrivacyPolicy();
        windowHandles = getWindowHandles();
        driver.switchTo().window(windowHandles.get(1));
        Assert.assertEquals(driver.getCurrentUrl(), "https://voltmoney.in/privacy?showHeader=false");
        driver.close();
    }
}
