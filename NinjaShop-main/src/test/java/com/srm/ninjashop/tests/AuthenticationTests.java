package com.srm.ninjashop.tests;
import com.srm.ninjashop.config.ConfigReader;
import com.srm.ninjashop.model.TestUser;
import com.srm.ninjashop.pages.AccountPage;
import com.srm.ninjashop.pages.AccountSuccessPage;
import com.srm.ninjashop.pages.LoginPage;
import com.srm.ninjashop.utils.ExcelUtils;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AuthenticationTests extends BaseTest {

    private static final String TEST_DATA_SHEET = "Sheet1";
    private static final List<TestUser> EXCEL_USERS =
            ExcelUtils.getUsers(ConfigReader.getTestDataPath(), TEST_DATA_SHEET);

    @Test(description = "Verify registration using the Excel row data as provided")
    public void verifyUserCanRegisterSuccessfully() {
        TestUser testUser = TestUser.createUniqueFrom(EXCEL_USERS.get(0));

        AccountSuccessPage accountSuccessPage = homePage.openRegisterPage()
                .waitForPage()
                .registerUser(
                        testUser.getFirstName(),
                        testUser.getLastName(),
                        testUser.getEmail(),
                        testUser.getTelephone(),
                        testUser.getPassword());

        Assert.assertEquals(accountSuccessPage.getSuccessHeading(), "Your Account Has Been Created!");
        Assert.assertTrue(accountSuccessPage.clickContinue().isAccountPageLoaded(),
                "My Account page should be displayed after successful registration.");
    }

    @Test(dataProvider = "loginCredentials", description = "Verify login success and login failure using DataProvider")
    public void verifyLoginScenarios(TestUser excelUser, boolean validPassword) {
        TestUser testUser = TestUser.createUniqueFrom(excelUser);
        homePage.openRegisterPage()
                .waitForPage()
                .registerUser(
                        testUser.getFirstName(),
                        testUser.getLastName(),
                        testUser.getEmail(),
                        testUser.getTelephone(),
                        testUser.getPassword())
                .clickContinue()
                .waitForPage();
        homePage.open().logout();

        String password = validPassword ? testUser.getPassword() : testUser.getPassword() + "Wrong";

        LoginPage loginPage = homePage.openLoginPage().waitForPage();

        if (validPassword) {
            AccountPage accountPage = loginPage.login(testUser.getEmail(), password).waitForPage();
            Assert.assertTrue(accountPage.isAccountPageLoaded(),
                    "My Account page should be shown after valid login.");
        } else {
            Assert.assertTrue(
                    loginPage.loginExpectingFailure(testUser.getEmail(), password)
                            .getLoginWarningMessage()
                            .contains("No match for E-Mail Address and/or Password"),
                    "Expected login warning message should be shown for invalid credentials.");
        }
    }

    @Test(description = "Verify logout returns the user to the home page")
    public void verifyLogoutRedirectsToHomePage() {
        TestUser testUser = TestUser.createUniqueFrom(EXCEL_USERS.get(1));
        homePage.openRegisterPage()
                .waitForPage()
                .registerUser(
                        testUser.getFirstName(),
                        testUser.getLastName(),
                        testUser.getEmail(),
                        testUser.getTelephone(),
                        testUser.getPassword())
                .clickContinue()
                .waitForPage();

        homePage.open().logout();
        homePage.open();

        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be reachable after logout.");
        Assert.assertFalse(homePage.isUserLoggedIn(), "User should not remain logged in after logout.");
    }

    @DataProvider(name = "loginCredentials")
    public Object[][] loginCredentials() {
        List<Object[]> loginData = new ArrayList<>();
        for (TestUser user : EXCEL_USERS) {
            loginData.add(new Object[]{user, true});
            loginData.add(new Object[]{user, false});
        }
        return loginData.toArray(new Object[0][]);
    }
}
