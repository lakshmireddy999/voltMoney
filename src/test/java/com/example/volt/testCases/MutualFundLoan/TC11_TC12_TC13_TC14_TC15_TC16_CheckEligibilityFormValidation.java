package com.example.volt.testCases.MutualFundLoan;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.volt.PageObjects.MutualFundLoan.MFLoanPage;
import com.example.volt.testCases.BaseTest;
import com.github.javafaker.Faker;

public class TC11_TC12_TC13_TC14_TC15_TC16_CheckEligibilityFormValidation extends BaseTest {
    MFLoanPage mfLoanPage;
    Faker faker;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        mfLoanPage = new MFLoanPage(driver);
        faker = new Faker();
    }

    @Test(description = "This tests validates form" , groups = {"regression"})
    public void formValidation() {
        startDriver();
        mfLoanPage.setMobileNumber("eugiuw^%(@)");
        Assert.assertEquals(mfLoanPage.getMobileNumber(), "");

        mfLoanPage.setMobileNumber(faker.number().digits(7).toString());
        mfLoanPage.clickTxtPan();
        Assert.assertTrue(mfLoanPage.verifyMobileError());

        mfLoanPage.setMobileNumber(faker.number().digits(12).toString());
        Assert.assertEquals(mfLoanPage.getMobileNumber().length(), 10);

        mfLoanPage.setMobileNumber(faker.number().digits(10).toString());
        mfLoanPage.clickTxtPan();
        Assert.assertFalse(mfLoanPage.verifyMobileError());

        mfLoanPage.setPanNumber("ADVPR78652");
        Assert.assertTrue(mfLoanPage.verifyPanError());

        mfLoanPage.setPanNumber(faker.lorem().characters(10));
        Assert.assertTrue(mfLoanPage.verifyPanError());

        mfLoanPage.setPanNumber("ABDPY6754D");
        Assert.assertFalse(mfLoanPage.verifyPanError());

        mfLoanPage.clickCheckEligibilityForFree();
    }
}
