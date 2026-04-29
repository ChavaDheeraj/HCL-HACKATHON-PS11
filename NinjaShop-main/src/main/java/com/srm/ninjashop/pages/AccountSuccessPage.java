package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountSuccessPage extends BasePage {

    private final By successHeading = By.xpath("//h1[contains(normalize-space(),'Your Account Has Been Created')]");
    private final By continueButton = By.linkText("Continue");

    public AccountSuccessPage(WebDriver driver) {
        super(driver);
    }

    public String getSuccessHeading() {
        return getText(successHeading);
    }

    public AccountPage clickContinue() {
        click(continueButton);
        return new AccountPage(driver);
    }
}
