package com.srm.ninjashop.tests;

import com.srm.ninjashop.pages.CartPage;
import com.srm.ninjashop.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidationTests extends BaseTest {

    private static final String PRODUCT_NAME = "Samsung SyncMaster 941BW";

    @Test(description = "Submit registration with empty fields and verify validation messages are shown")
    public void verifyRegistrationValidationMessagesForEmptyFields() {
        RegisterPage registerPage = homePage.openRegisterPage().waitForPage().submitEmptyRegistration();

        Assert.assertTrue(registerPage.getFieldError("firstname").contains("First Name must be between 1 and 32 characters"));
        Assert.assertTrue(registerPage.getFieldError("lastname").contains("Last Name must be between 1 and 32 characters"));
        Assert.assertTrue(registerPage.getFieldError("telephone").contains("Telephone must be between 3 and 32 characters"));
        Assert.assertTrue(registerPage.getFieldError("password").contains("Password must be between 4 and 20 characters"));
        Assert.assertTrue(registerPage.getPrivacyPolicyWarning().contains("Privacy Policy"),
                "Privacy policy warning should be displayed.");
    }

    @Test(description = "Enter an invalid email format during registration and verify browser validation")
    public void verifyInvalidEmailFormatValidation() {
        RegisterPage registerPage = homePage.openRegisterPage().waitForPage().submitWithInvalidEmail("invalid-email");

        Assert.assertFalse(registerPage.isEmailFieldHtml5Valid(), "E-Mail field should be invalid for malformed input.");
        Assert.assertFalse(registerPage.getEmailFieldValidationMessage().isBlank(),
                "Browser validation message should be available for invalid email.");
    }

//    @Test(description = "Attempt checkout from cart and capture the stock warning shown by the live site")
//    public void verifyCheckoutShowsStockWarning() {
//        registerNewUser();
//        homePage.searchProduct(PRODUCT_NAME).openProduct(PRODUCT_NAME).addToCart();
//
//        CartPage cartPage = homePage.openCartPage();
//        cartPage.clickCheckout();
//
//        Assert.assertTrue(cartPage.getStockWarningMessage().contains("not available in the desired quantity or not in stock"),
//                "Stock warning should be displayed when checkout is blocked by the demo site.");
//        captureEvidence("checkout_stock_warning");
//    }
}
