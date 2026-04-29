package com.srm.ninjashop.tests;

import com.srm.ninjashop.pages.CartPage;
import com.srm.ninjashop.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {

    private static final String PRODUCT_NAME = "Nikon D300";

    @Test(description = "Add a product to cart and verify it appears with the correct name and price")
    public void verifyProductCanBeAddedToCart() {
        ProductPage productPage = homePage.searchProduct(PRODUCT_NAME).openProduct(PRODUCT_NAME);
        String expectedName = productPage.getProductName();
        String expectedPrice = productPage.getProductPrice();
        productPage.addToCart();

        CartPage cartPage = homePage.openCartPage();

        Assert.assertEquals(cartPage.getFirstProductName(), expectedName);
        Assert.assertTrue(cartPage.getFirstProductPrice().contains(expectedPrice.replace("\n", "")),
                "Cart price should match product page price.");
    }

    @Test(description = "Update the quantity of a product in the cart and verify the line total changes")
    public void verifyCartQuantityUpdateChangesLineTotal() {
        homePage.searchProduct(PRODUCT_NAME).openProduct(PRODUCT_NAME).addToCart();
        CartPage cartPage = homePage.openCartPage();
        double initialLineTotal = cartPage.getLineTotalAmount();

        cartPage.updateQuantity("2");
        double updatedLineTotal = cartPage.getLineTotalAmount();

        Assert.assertTrue(updatedLineTotal > initialLineTotal, "Line total should increase after quantity update.");
    }

    @Test(description = "Remove a product from the cart and verify the cart becomes empty")
    public void verifyProductCanBeRemovedFromCart() {
        homePage.searchProduct(PRODUCT_NAME).openProduct(PRODUCT_NAME).addToCart();
        CartPage cartPage = homePage.openCartPage();

        cartPage.removeProduct();

        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removing the product.");
    }

    @Test(description = "Verify the cart icon in the header reflects the correct item count")
    public void verifyHeaderCartCountReflectsAddedItems() {
        homePage.searchProduct(PRODUCT_NAME).openProduct(PRODUCT_NAME).addToCart();

        Assert.assertTrue(homePage.getCartItemCountText().contains("1 item(s)"),
                "Header cart count should show one item after adding a product.");
    }
}
