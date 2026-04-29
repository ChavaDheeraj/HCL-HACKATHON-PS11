package com.srm.ninjashop.tests;

import com.srm.ninjashop.pages.CategoryPage;
import com.srm.ninjashop.pages.ProductPage;
import com.srm.ninjashop.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductTests extends BaseTest {

    @Test(description = "Search for a product by keyword and verify results are displayed")
    public void verifySearchDisplaysResults() {
        SearchPage searchPage = homePage.searchProduct("MacBook");

        Assert.assertTrue(searchPage.hasResults(), "Search results should be displayed.");
        Assert.assertTrue(searchPage.getResultsCount() > 0, "At least one search result should be listed.");
    }

    @Test(description = "Navigate to a top category and verify products are listed")
    public void verifyTopCategoryDisplaysProducts() {
        CategoryPage categoryPage = homePage.openTopCategory("Desktops");

        Assert.assertTrue(categoryPage.getListedProductsCount() > 0,
                "Selected category should list at least one product.");
    }

    @Test(description = "Open a product detail page and verify product name and price are shown")
    public void verifyProductDetailsShowNameAndPrice() {
        ProductPage productPage = homePage.searchProduct("MacBook").openProduct("MacBook");

        Assert.assertEquals(productPage.getProductName(), "MacBook");
        Assert.assertFalse(productPage.getProductPrice().isBlank(), "Product price should be visible.");
        Assert.assertTrue(productPage.isAddToCartButtonDisplayed(), "Add to Cart button should be displayed.");
    }

    @Test(description = "Verify empty search shows the no-results message and capture evidence")
    public void verifyEmptySearchShowsNoResultsMessage() {
        SearchPage searchPage = homePage.searchWithoutKeyword();

        Assert.assertEquals(
                searchPage.getNoResultMessage(),
                "There is no product that matches the search criteria.");
        captureEvidence("empty_search_validation");
    }
}
