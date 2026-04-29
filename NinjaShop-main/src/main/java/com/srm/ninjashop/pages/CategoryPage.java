package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoryPage extends BasePage {

    private final By listedProducts = By.cssSelector(".product-layout");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public int getListedProductsCount() {
        return getElementCount(listedProducts);
    }

    public ProductPage openProduct(String productName) {
        click(By.xpath("//div[@class='caption']//a[normalize-space()='" + productName + "']"));
        return new ProductPage(driver);
    }
}
