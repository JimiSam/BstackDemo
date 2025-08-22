package com.Nopcommerce.actionDriver;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Nopcommerce.base.Baseclass;
import com.Nopcommerce.utilities.ExtentManager;


public class ActionDriver {
	
	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger=Baseclass.logger;
	
	public ActionDriver(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		int explicitwait=Integer.parseInt(Baseclass.getProp().getProperty("explicitwait"));
		this.wait=new WebDriverWait(driver, Duration.ofSeconds(explicitwait));
		logger.info("WebDriver instance created");
	}
	
	public void waitelementtoBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element is not clickable "+e.getMessage());
		}
	}
	
	public void waitelementtoBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not visible "+e.getMessage());
		}
	}
	
	public void click(By by) {
		try {
			waitelementtoBeClickable(by);
			driver.findElement(by).click();
			ExtentManager.logPass("Clicked on: " + by);
			logger.info("Clicked an element");
		} catch (Exception e) {
			//System.out.println("Unable to click element "+e.getMessage());
			ExtentManager.logFailWithScreenshot(driver, "Failed to enter text in: " + by);
			logger.error("Not clicked "+e.getMessage());
		}
	}
	
	public void enterText(By by,String value) {
		try {
			waitelementtoBeVisible(by);
			driver.findElement(by).sendKeys(value);
			logger.info("Value entered");
		} catch (Exception e) {
			//System.out.println("Unable to enter text "+e.getMessage());
			logger.error("Value not entered "+e.getMessage());
		}
	}
	
	public String getText(By by) {
		try {
			waitelementtoBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			logger.error("Unable to get "+e.getMessage());
			return "";
		}
		
	}
	public List<WebElement> getMultiples(By by) {
		try {
			waitelementtoBeVisible(by);
			return driver.findElements(by);
		} catch (Exception e) {
			logger.error("Unable to get "+e.getMessage());
			return null;
		}
		
	}
	
	public boolean compareText(By by,String expectedtext) {
		try {
			waitelementtoBeVisible(by);
			String actualText=driver.findElement(by).getText();
			if(expectedtext.equals(actualText)) {
				System.out.println("Text are matching "+actualText+" equals "+expectedtext);
				return true;
			}
			else {
				System.out.println("Text are not matching "+actualText+" not equals "+expectedtext);
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to compare "+e.getMessage());
		}
		return false;
		
		
	}
	
	public boolean textDisplayed(By by) {
		try {
			waitelementtoBeVisible(by);
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			System.out.println("Element not displayed "+e.getMessage());
			return false;
		}
		
		
	}
	
	public void scrollToElement(By by) {
		try {
			waitelementtoBeVisible(by);
			JavascriptExecutor js=(JavascriptExecutor)driver;
			WebElement element=driver.findElement(by);
			js.executeScript("argument[0],scrollIntoView(true);",element);
		} catch (Exception e) {
			System.out.println("Unable to locate element "+e.getMessage());
		}
	}
	
	public boolean sidemenus(By by) {
		List<WebElement>sidemenu=driver.findElements(by);
		System.out.println("Number of sidebar items: " + sidemenu.size());
		return sidemenu.size()==12;
		
	}
	public int topmenus(By by) {
		List<WebElement>topmenu=driver.findElements(by);
		System.out.println("Number of navbar items: " + topmenu.size());
		return topmenu.size();
		
	}
	
	public boolean isSelected(By by) {
	    return driver.findElement(by).isSelected();
	}

	public String getTitle() {
		try {
			String title = driver.getTitle();
	        ExtentManager.logPass("Title on: " + title);
	        return title;
		} catch (Exception e) {
			ExtentManager.logFailWithScreenshot(driver, "Failed to gettitle");
			logger.error("Unable to get "+e.getMessage());
			return "";
		}
		
	}
	
	public void selectFromList(By by, String value) {
	    List<WebElement> elements = driver.findElements(by);
	    for (WebElement element : elements) {
	    	System.out.println(element.getText());
	        if (element.getText().equalsIgnoreCase(value)) {
	            element.click();
	            break;
	        }
	    }
	}
	
	public void dropdown(By locator, String value) {
	    try {
	        WebElement element = driver.findElement(locator);  // Find element using By
	        Select select = new Select(element);
	        select.selectByVisibleText(value);
	        System.out.println("Selected value: " + value);
	    } catch (Exception e) {
	        System.out.println("Unable to select value: " + value + " | Error: " + e.getMessage());
	    }
	}


}
