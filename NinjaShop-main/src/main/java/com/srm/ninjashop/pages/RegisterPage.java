package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private final By firstNameField = By.id("input-firstname");
    private final By lastNameField = By.id("input-lastname");
    private final By emailField = By.id("input-email");
    private final By telephoneField = By.id("input-telephone");
    private final By passwordField = By.id("input-password");
    private final By confirmPasswordField = By.id("input-confirm");
    private final By privacyPolicyCheckbox = By.name("agree");
    private final By continueButton = By.cssSelector("input[value='Continue']");
    private final By successHeading = By.xpath("//h1[contains(normalize-space(),'Your Account Has Been Created')]");
    private final By warningAlert = By.cssSelector(".alert.alert-danger");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage waitForPage() {
        waitForVisibility(firstNameField);
        return this;
    }

    public AccountSuccessPage registerUser(
            String firstName,
            String lastName,
            String email,
            String telephone,
            String password) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(emailField, email);
        type(telephoneField, telephone);
        type(passwordField, password);
        type(confirmPasswordField, password);
        if (!isElementSelected(privacyPolicyCheckbox)) {
            click(privacyPolicyCheckbox);
        }
        click(continueButton);
        return new AccountSuccessPage(driver);
    }

    public RegisterPage submitEmptyRegistration() {
        click(continueButton);
        return this;
    }

    public RegisterPage submitWithInvalidEmail(String invalidEmail) {
        type(firstNameField, "Hackathon");
        type(lastNameField, "User");
        type(emailField, invalidEmail);
        type(telephoneField, "9876543210");
        type(passwordField, "Test@12345");
        type(confirmPasswordField, "Test@12345");
        click(continueButton);
        return this;
    }

    public boolean isRegistrationSuccessful() {
        return isDisplayed(successHeading);
    }

    public String getSuccessHeading() {
        return getText(successHeading);
    }

    public String getFieldError(String fieldName) {
        By fieldError = By.xpath("//input[@name='" + fieldName + "']/following-sibling::div[contains(@class,'text-danger')]");
        return getText(fieldError);
    }

    public String getPrivacyPolicyWarning() {
        return getText(warningAlert);
    }

    public boolean isEmailFieldHtml5Valid() {
        return isHtml5Valid(emailField);
    }

    public String getEmailFieldValidationMessage() {
        return getHtml5ValidationMessage(emailField);
    }
}
