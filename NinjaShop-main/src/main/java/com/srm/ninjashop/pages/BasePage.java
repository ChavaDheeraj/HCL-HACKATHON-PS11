package com.srm.ninjashop.pages;

import com.srm.ninjashop.config.ConfigReader;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getTimeout()));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitForAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        waitForClickability(locator).click();
    }

    protected void jsClick(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void type(By locator, String value) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected String getText(By locator) {
        try {
            return waitForVisibility(locator).getText().trim();
        } catch (StaleElementReferenceException exception) {
            return waitForVisibility(locator).getText().trim();
        }
    }

    protected String getAttribute(By locator, String attributeName) {
        return waitForVisibility(locator).getAttribute(attributeName);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (Exception exception) {
            return false;
        }
    }

    protected int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }

    protected void waitForUrlContains(String value) {
        wait.until(ExpectedConditions.urlContains(value));
    }

    protected void waitForText(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void selectByVisibleText(By locator, String value) {
        new Select(waitForVisibility(locator)).selectByVisibleText(value);
    }

    protected void scrollIntoView(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected boolean isElementSelected(By locator) {
        return waitForVisibility(locator).isSelected();
    }

    protected boolean isHtml5Valid(By locator) {
        Object result = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].checkValidity();", waitForVisibility(locator));
        return Boolean.TRUE.equals(result);
    }

    protected String getHtml5ValidationMessage(By locator) {
        Object result = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].validationMessage;", waitForVisibility(locator));
        return result == null ? "" : result.toString().trim();
    }

    protected double parsePrice(String amount) {
        String normalized = amount.replaceAll("[^0-9.]", "");
        return normalized.isEmpty() ? 0.0 : Double.parseDouble(normalized);
    }
}
