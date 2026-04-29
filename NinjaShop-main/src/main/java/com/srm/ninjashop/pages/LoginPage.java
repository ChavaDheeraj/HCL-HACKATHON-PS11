package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailField = By.id("input-email");
    private final By passwordField = By.id("input-password");
    private final By loginButton = By.cssSelector("input[value='Login']");
    private final By warningAlert = By.cssSelector(".alert.alert-danger");
    private final By returningCustomerHeading = By.xpath("//h2[normalize-space()='Returning Customer']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage waitForPage() {
        waitForVisibility(returningCustomerHeading);
        return this;
    }

    public AccountPage login(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
        click(loginButton);
        return new AccountPage(driver);
    }

    public LoginPage loginExpectingFailure(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
        click(loginButton);
        return this;
    }

    public String getLoginWarningMessage() {
        return getText(warningAlert);
    }
}
