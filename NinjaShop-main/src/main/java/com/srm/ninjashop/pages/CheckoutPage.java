package com.srm.ninjashop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private final By checkoutHeading = By.xpath("//h1[normalize-space()='Checkout']");
    private final By returningCustomerHeading = By.xpath("//h2[normalize-space()='Returning Customer']");
    private final By newAddressRadio = By.cssSelector("input[name='payment_address'][value='new']");
    private final By firstNameField = By.id("input-payment-firstname");
    private final By lastNameField = By.id("input-payment-lastname");
    private final By companyField = By.id("input-payment-company");
    private final By addressOneField = By.id("input-payment-address-1");
    private final By cityField = By.id("input-payment-city");
    private final By postCodeField = By.id("input-payment-postcode");
    private final By countryDropdown = By.id("input-payment-country");
    private final By regionDropdown = By.id("input-payment-zone");
    private final By paymentAddressContinueButton = By.id("button-payment-address");
    private final By shippingAddressContinueButton = By.id("button-shipping-address");
    private final By shippingMethodContinueButton = By.id("button-shipping-method");
    private final By termsCheckbox = By.cssSelector("input[name='agree']");
    private final By paymentMethodContinueButton = By.id("button-payment-method");
    private final By confirmOrderButton = By.id("button-confirm");
    private final By paymentAddressWarning = By.cssSelector("#collapse-payment-address .text-danger");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutPageDisplayed() {
        return isDisplayed(checkoutHeading);
    }

    public boolean isReturningCustomerPromptDisplayed() {
        return isDisplayed(returningCustomerHeading);
    }

    public CheckoutPage chooseNewAddressIfAvailable() {
        if (getElementCount(newAddressRadio) > 0) {
            jsClick(newAddressRadio);
        }
        return this;
    }

    public CheckoutPage fillDeliveryDetails(
            String firstName,
            String lastName,
            String company,
            String addressLineOne,
            String city,
            String postCode,
            String country,
            String region) {
        chooseNewAddressIfAvailable();
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(companyField, company);
        type(addressOneField, addressLineOne);
        type(cityField, city);
        type(postCodeField, postCode);
        selectByVisibleText(countryDropdown, country);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector("#input-payment-zone option"), 1));
        selectByVisibleText(regionDropdown, region);
        click(paymentAddressContinueButton);
        return this;
    }

    public CheckoutPage continueShippingAddress() {
        click(shippingAddressContinueButton);
        return this;
    }

    public CheckoutPage continueShippingMethod() {
        click(shippingMethodContinueButton);
        return this;
    }

    public CheckoutPage continuePaymentMethod() {
        if (!isElementSelected(termsCheckbox)) {
            click(termsCheckbox);
        }
        click(paymentMethodContinueButton);
        return this;
    }

    public CheckoutSuccessPage confirmOrder() {
        click(confirmOrderButton);
        return new CheckoutSuccessPage(driver);
    }

    public CheckoutPage submitDeliveryDetailsWithMissingFields() {
        chooseNewAddressIfAvailable();
        type(firstNameField, "");
        type(lastNameField, "");
        type(companyField, "");
        type(addressOneField, "");
        type(cityField, "");
        type(postCodeField, "");
        click(paymentAddressContinueButton);
        return this;
    }

    public String getPaymentAddressWarning() {
        return getText(paymentAddressWarning);
    }
}
