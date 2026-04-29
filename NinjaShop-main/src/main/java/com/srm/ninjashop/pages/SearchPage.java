package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage extends BasePage {

    private final By resultProducts = By.cssSelector(".product-layout");
    private final By noResultMessage = By.xpath("//p[contains(normalize-space(),'There is no product that matches the search criteria.')]");
    private final By searchHeading = By.xpath("//h1[normalize-space()='Search - ']");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasResults() {
        return getElementCount(resultProducts) > 0;
    }

    public int getResultsCount() {
        return getElementCount(resultProducts);
    }

    public boolean isSearchResultPageDisplayedForEmptyKeyword() {
        return isDisplayed(searchHeading);
    }

    public ProductPage openProduct(String productName) {
        click(By.xpath("//div[@class='product-thumb']//a[normalize-space()='" + productName + "']"));
        return new ProductPage(driver);
    }

    public String getNoResultMessage() {
        return getText(noResultMessage);
    }
}
