package com.srm.ninjashop.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ExtentReportManager {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();
    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getReporter() {
        if (extentReports == null) {
            String reportDirectory = System.getProperty("user.dir") + File.separator + "reports";
            String reportPath = reportDirectory + File.separator + "ExtentReport_"
                    + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".html";

            new File(reportDirectory).mkdirs();

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setReportName("TutorialsNinja Automation Results");
            sparkReporter.config().setDocumentTitle("NinjaShop Hackathon Report");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Application", "TutorialsNinja Demo Store");
            extentReports.setSystemInfo("Framework", "Selenium + TestNG");
        }
        return extentReports;
    }

    public static void createTest(String testName) {
        EXTENT_TEST.set(getReporter().createTest(testName));
    }

    public static ExtentTest getTest() {
        return EXTENT_TEST.get();
    }

    public static synchronized void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
