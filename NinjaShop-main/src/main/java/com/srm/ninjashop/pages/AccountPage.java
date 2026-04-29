package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends BasePage {

    private final By accountHeading = By.xpath("//h2[normalize-space()='My Account']");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage waitForPage() {
        waitForVisibility(accountHeading);
        return this;
    }

    public boolean isAccountPageLoaded() {
        return isDisplayed(accountHeading);
    }
}
