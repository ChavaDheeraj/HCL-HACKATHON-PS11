package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By cartRows = By.cssSelector(".table-responsive tbody tr");
    private final By updateButton = By.cssSelector("button[data-original-title='Update']");
    private final By removeButton = By.cssSelector("button[data-original-title='Remove']");
    private final By quantityField = By.cssSelector("input[name*='quantity']");
    private final By lineTotal = By.cssSelector(".table-responsive tbody tr td:nth-child(6)");
    private final By emptyCartMessage = By.cssSelector("#content p");
    private final By checkoutButton = By.cssSelector("a.btn.btn-primary[href*='checkout/checkout']");
    private final By stockWarningMessage = By.cssSelector(".alert.alert-danger");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasProducts() {
        return getElementCount(cartRows) > 0;
    }

    public String getFirstProductName() {
        return getText(By.cssSelector(".table-responsive tbody tr td:nth-child(2) a"));
    }

    public String getFirstProductPrice() {
        return getText(By.cssSelector(".table-responsive tbody tr td:nth-child(5)"));
    }

    public double getLineTotalAmount() {
        return parsePrice(getText(lineTotal));
    }

    public void updateQuantity(String quantity) {
        type(quantityField, quantity);
        click(updateButton);
        waitForText(By.id("cart-total"), quantity + " item(s)");
    }

    public void removeProduct() {
        click(removeButton);
        wait.until(driver -> driver.getPageSource().contains("Your shopping cart is empty!"));
    }

    public boolean isCartEmpty() {
        return getText(emptyCartMessage).contains("Your shopping cart is empty!");
    }

    public CheckoutPage clickCheckout() {
        scrollIntoView(checkoutButton);
        click(checkoutButton);
        return new CheckoutPage(driver);
    }

    public String getStockWarningMessage() {
        return getText(stockWarningMessage);
    }
}
