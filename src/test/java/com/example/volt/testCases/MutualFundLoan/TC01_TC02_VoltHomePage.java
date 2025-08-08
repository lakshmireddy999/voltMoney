package com.example.volt.testCases.MutualFundLoan;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.volt.PageObjects.MutualFundLoan.MFLoanPage;
import com.example.volt.testCases.BaseTest;

public class TC01_TC02_VoltHomePage extends BaseTest{

    MFLoanPage mfLoanPage;
    
    @BeforeClass(alwaysRun = true)
    public void setup(){
        mfLoanPage = new MFLoanPage(driver);
    }

    @Test(description = "This test the loading of Home Page")
    public void validateHomepage(){
        startDriver();
        mfLoanPage.clickVoltHome();
        Assert.assertEquals(driver.getCurrentUrl(), "https://voltmoney.in/");
    }
}
