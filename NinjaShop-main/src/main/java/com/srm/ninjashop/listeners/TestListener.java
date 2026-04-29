package com.srm.ninjashop.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.srm.ninjashop.utils.ExtentReportManager;
import com.srm.ninjashop.utils.ScreenshotUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.getReporter();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureScreenshot(result.getMethod().getMethodName());
        ExtentReportManager.getTest()
                .fail(result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReport();
    }
}
