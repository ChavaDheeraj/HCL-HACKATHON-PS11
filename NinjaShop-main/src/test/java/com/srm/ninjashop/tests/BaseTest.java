package com.srm.ninjashop.tests;

import com.srm.ninjashop.driver.DriverFactory;
import com.srm.ninjashop.model.TestUser;
import com.srm.ninjashop.pages.AccountPage;
import com.srm.ninjashop.pages.AccountSuccessPage;
import com.srm.ninjashop.pages.HomePage;
import com.srm.ninjashop.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initializeDriver();
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver).open();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected TestUser registerNewUser() {
        TestUser testUser = TestUser.createUnique();
        AccountSuccessPage accountSuccessPage = homePage.openRegisterPage()
                .waitForPage()
                .registerUser(
                        testUser.getFirstName(),
                        testUser.getLastName(),
                        testUser.getEmail(),
                        testUser.getTelephone(),
                        testUser.getPassword());
        accountSuccessPage.clickContinue().waitForPage();
        return testUser;
    }

    protected TestUser registerNewUserAndLogout() {
        TestUser testUser = registerNewUser();
        homePage.open().logout();
        return testUser;
    }

    protected AccountPage loginWith(TestUser testUser) {
        return homePage.openLoginPage()
                .waitForPage()
                .login(testUser.getEmail(), testUser.getPassword())
                .waitForPage();
    }

    protected String captureEvidence(String screenshotName) {
        return ScreenshotUtils.captureScreenshot(screenshotName);
    }
}
