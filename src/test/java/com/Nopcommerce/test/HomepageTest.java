package com.Nopcommerce.test;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Nopcommerce.base.Baseclass;
import com.Nopcommerce.pages.Homepage;
import com.Nopcommerce.utilities.ExtentManager;


public class HomepageTest extends Baseclass{
	private Homepage homepage;
	@BeforeMethod
	public void setuppages(Method method) {
		 String testName = method.getName();   // <-- This gives your @Test method name
	        System.out.println("Running Test: " + testName);

	        ExtentManager.startTest(testName);
	        homepage = new Homepage(getDriver());
		
	}
	 @Test
	    public void testLoginPageTitle() {

		 	
		 	String expectedTitle = "StackDemo";
		    
		    String actualTitle = homepage.titleverify();
		    ExtentManager.logStep("Page Title: " + actualTitle);

		    if (actualTitle.equals(expectedTitle)) {
		        ExtentManager.logPass("Title matched successfully");
		    } else {
		        ExtentManager.logFailWithScreenshot(driver, 
		            "Title verification failed. Expected: '" + expectedTitle + "' but found: '" + actualTitle + "'");
		    }

		    // Still keep TestNG assertion so test result is marked Fail
		    Assert.assertEquals(actualTitle, expectedTitle);
	    }

	@Test
	public void verifyHRMlogo() {
			
	        try {
	            Assert.assertTrue(homepage.IsLogoDisplayed(), "Logo not displayed");
	            ExtentManager.logPass("Logo displayed successfully");
	            
	        } catch (AssertionError e) {
	        	 ExtentManager.logFailWithScreenshot(driver,  "Title verification failed. Expected: ");
	 		     
	            throw e; // rethrow so TestNG also marks it as failed
	        }
	
	}
	
	@Test
	public void verifyTopmenu() {
		
		try {
			homepage.verifyMenus();
            ExtentManager.logPass("Menus displayed successfully");
            
        } catch (AssertionError e) {
        	 ExtentManager.logFailWithScreenshot(driver,  "Menu verification failed.  ");
 		     
            throw e; // rethrow so TestNG also marks it as failed
        }
		
	}
	
	@Test
	public void verifyProductCount() {
		
		try {
			int count=homepage.verifyCount();
            ExtentManager.logPass("Products displayed successfully: "+count);
            
        } catch (AssertionError e) {
        	 ExtentManager.logFailWithScreenshot(driver,  "Product count verification failed.  ");
 		     
            throw e; // rethrow so TestNG also marks it as failed
        }
		
	}
	
	@Test
	public void verifyProductname() {
		
		try {
			String pdtname=homepage.verifyPdtName();
			System.out.println(pdtname);
            ExtentManager.logPass("Products displayed successfully");
            
        } catch (AssertionError e) {
        	 ExtentManager.logFailWithScreenshot(driver,  "Product name verification failed.  ");
 		     
            throw e; // rethrow so TestNG also marks it as failed
        }
		
	}
	
	
	
	@AfterMethod
    public void tearDown() {
        // Flush the report
        ExtentManager.endTest();
    }
}
