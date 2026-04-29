package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutSuccessPage extends BasePage {

    private final By successHeading = By.xpath("//h1[contains(normalize-space(),'Your order has been placed')]");

    public CheckoutSuccessPage(WebDriver driver) {
        super(driver);
    }

    public String getSuccessHeading() {
        return getText(successHeading);
    }
}
