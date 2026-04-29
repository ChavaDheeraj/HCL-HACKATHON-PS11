package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends BasePage {

    private final By productName = By.cssSelector("#content h1");
    private final By priceBlock = By.cssSelector(".list-unstyled h2");
    private final By addToCartButton = By.id("button-cart");
    private final By successAlert = By.cssSelector(".alert.alert-success");
    private final By optionSelects = By.cssSelector("select[id^='input-option']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getProductPrice() {
        return getText(priceBlock);
    }

    public boolean isAddToCartButtonDisplayed() {
        return isDisplayed(addToCartButton);
    }

    public ProductPage addToCart() {
        selectRequiredOptionsIfPresent();
        scrollIntoView(addToCartButton);
        click(addToCartButton);
        waitForVisibility(successAlert);
        return this;
    }

    public String getSuccessMessage() {
        return getText(successAlert);
    }

    private void selectRequiredOptionsIfPresent() {
        for (WebElement element : driver.findElements(optionSelects)) {
            Select select = new Select(element);
            String selectedValue = select.getFirstSelectedOption().getAttribute("value");
            if (select.getOptions().size() > 1 && (selectedValue == null || selectedValue.isBlank())) {
                select.selectByIndex(1);
            }
        }
    }
}
