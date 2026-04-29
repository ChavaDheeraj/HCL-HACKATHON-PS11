package com.srm.ninjashop.tests;


import com.srm.ninjashop.pages.CartPage;
import com.srm.ninjashop.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {

    private static final String PRODUCT_NAME = "Samsung Galaxy Tab 10.1";

    @Test(description = "Attempt checkout as a logged-in user and capture the out-of-stock block shown by the live site")
    public void verifyLoggedInCheckoutShowsStockBlock() {
        registerNewUser();
        homePage.searchProduct(PRODUCT_NAME).openProduct(PRODUCT_NAME).addToCart();

        CartPage cartPage = homePage.openCartPage();
        cartPage.clickCheckout();

        Assert.assertTrue(cartPage.getStockWarningMessage().contains("not available in the desired quantity or not in stock"),
                "Live site should block checkout with the stock warning.");
        captureEvidence("logged_in_checkout_blocked");
    }

    @Test(description = "Attempt checkout without logging in and capture the out-of-stock block shown by the live site")
    public void verifyCheckoutWithoutLoginShowsStockBlock() {
        homePage.searchProduct(PRODUCT_NAME).openProduct(PRODUCT_NAME).addToCart();

        CartPage cartPage = homePage.openCartPage();
        cartPage.clickCheckout();

        Assert.assertTrue(cartPage.getStockWarningMessage().contains("not available in the desired quantity or not in stock"),
                "Live site should block checkout with the stock warning.");
        captureEvidence("guest_checkout_blocked");
    }
}
