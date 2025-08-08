package com.example.volt.testCases.MutualFundLoan;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.volt.PageObjects.MutualFundLoan.MFLoanPage;
import com.example.volt.testCases.BaseTest;

public class TC04_TC05_TC07_verifyHeaderHyperLinks extends BaseTest {
    MFLoanPage mfLoanPage;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        mfLoanPage = new MFLoanPage(driver);
    }

    @Test(description = "This test url redirection to various header elements", groups={"regression"})
    public void validateHeaderHyperLinks() {
        startDriver();
        mfLoanPage.clickInterestCaluculator();
        Assert.assertEquals(driver.getCurrentUrl(),
                "https://voltmoney.in/interest-calculator-for-loan-against-mutual-funds");
        mfLoanPage.clickCheckEligibility();

        mfLoanPage.clickCapitalGainStatement();
        Assert.assertEquals(driver.getCurrentUrl(), "https://voltmoney.in/capital-gains-statement");
        mfLoanPage.clickCheckEligibility();

        mfLoanPage.clickMFDistributors();
        Assert.assertEquals(driver.getCurrentUrl(), "https://voltmoney.in/partner");
    }
}
