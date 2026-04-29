package com.srm.ninjashop.pages;

import com.srm.ninjashop.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By logo = By.cssSelector("#logo a");
    private final By myAccountMenu = By.cssSelector("a[title='My Account']");
    private final By registerLink = By.linkText("Register");
    private final By loginLink = By.linkText("Login");
    private final By logoutLink = By.linkText("Logout");
    private final By searchField = By.name("search");
    private final By searchButton = By.cssSelector("#search button");
    private final By cartTotal = By.id("cart-total");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(ConfigReader.getBaseUrl());
        waitForVisibility(logo);
        return this;
    }

    public RegisterPage openRegisterPage() {
        openMyAccountMenu();
        click(registerLink);
        return new RegisterPage(driver);
    }

    public LoginPage openLoginPage() {
        openMyAccountMenu();
        click(loginLink);
        return new LoginPage(driver);
    }

    public HomePage logout() {
        openMyAccountMenu();
        click(logoutLink);
        return this;
    }

    public SearchPage searchProduct(String productName) {
        type(searchField, productName);
        click(searchButton);
        return new SearchPage(driver);
    }

    public SearchPage searchWithoutKeyword() {
        type(searchField, "");
        click(searchButton);
        return new SearchPage(driver);
    }

    public CategoryPage openTopCategory(String categoryName) {
        click(By.xpath("//ul[@class='nav navbar-nav']//a[normalize-space()='" + categoryName + "']"));
        return new CategoryPage(driver);
    }

    public CartPage openCartPage() {
        click(By.linkText("Shopping Cart"));
        return new CartPage(driver);
    }

    public CheckoutPage openCheckoutPage() {
        click(By.linkText("Checkout"));
        return new CheckoutPage(driver);
    }

    public boolean isHomePageLoaded() {
        return isDisplayed(logo);
    }

    public boolean isUserLoggedIn() {
        openMyAccountMenu();
        boolean visible = isDisplayed(logoutLink);
        click(myAccountMenu);
        return visible;
    }

    public String getCartItemCountText() {
        return getText(cartTotal);
    }

    private void openMyAccountMenu() {
        click(myAccountMenu);
    }
}
