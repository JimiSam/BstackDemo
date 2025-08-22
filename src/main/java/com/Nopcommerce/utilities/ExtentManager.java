package com.Nopcommerce.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
	public static ExtentReports getReporter() {
        if (extent == null) {
            String reportPath = "C:\\Jimitha works\\automation\\eclipsenewone\\Nopcommerce\\reports\\report.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Automation Testing Report");
            spark.config().setReportName("NopCommerce Test Report");
            spark.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User Name", System.getProperty("user.name"));
        }
        return extent;
    }

    public static void startTest(String testName) {
        ExtentTest test = getReporter().createTest(testName);
        testThread.set(test);
    }
    
    public static void endTest() {
    	getReporter().flush();
    }

    public static ExtentTest getTest() {
        return testThread.get();
    }

    public static void logStep(String message) {
        getTest().log(Status.INFO, message);
    }

    public static void logPass(String message) {
        getTest().log(Status.PASS, message);
    }

    public static void logFail(String message) {
        getTest().log(Status.FAIL, message);
    }

    public static void logFailWithScreenshot(WebDriver driver, String message) {
        getTest().log(Status.FAIL, message);
        try {
            String screenshotPath = takeScreenshot(driver);
            getTest().addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String takeScreenshot(WebDriver driver) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destination = "C:\\Jimitha works\\automation\\eclipsenewone\\Nopcommerce\\src\\test\\resources\\screenshot_" + timestamp + ".png";
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(src, new File(destination));
        return destination;
    }

	
   
}
