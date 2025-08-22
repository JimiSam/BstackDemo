package com.Nopcommerce.base;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.Nopcommerce.utilities.ExtentManager;
import com.Nopcommerce.utilities.LoggerManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class Baseclass {
	protected static  Properties prop;
	protected WebDriver driver;
	public static final Logger logger=LoggerManager.getLogger(Baseclass.class);
	 protected static ExtentReports extent;
	    protected static ExtentTest test;
	@BeforeSuite
	public void load_prop() throws Exception {
		prop=new Properties();
		FileInputStream fis=new FileInputStream("C:\\Jimitha works\\automation\\eclipsenewone\\Nopcommerce\\src\\main\\resources\\config.properties");
		prop.load(fis);
		logger.info("config property file loaded");
		extent = ExtentManager.getReporter();
	}
		private void launch_browser() {
		String browser=prop.getProperty("browser");
		if(browser.equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
			logger.info("ChromeDriver initialized");
			
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			driver=new FirefoxDriver();
			logger.info("FirefoxDriver initialized");
			
		}
		else if(browser.equalsIgnoreCase("edge")) {
			driver=new EdgeDriver();
			logger.info("EdgeDriver initialized");
		}
		
	}
	@BeforeMethod
	public void setup() throws Exception {
		System.out.println("Setting up webdriver for " +this.getClass().getSimpleName());
		launch_browser();
		config();
		logger.info("WebDriver initialized");
		logger.trace("This is trace message");
		logger.debug("This is a debug message");
		logger.error("This is error message");
		logger.fatal("This is a fatal message");
		logger.warn("This is a warning message");
	}
	private void config() {
		int implicitwait=Integer.parseInt(prop.getProperty("implicitwait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitwait));
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
	}
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver=driver;
	}
	
	public static Properties getProp() {
		return prop;
	}
	
	
	@AfterMethod
	public void tearDown() {
		if(driver!=null) {
			//driver.quit();
		}
		logger.info("WebDriver closed");
	}
	
	
}
