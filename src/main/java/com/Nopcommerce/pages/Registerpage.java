package com.Nopcommerce.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Nopcommerce.actionDriver.ActionDriver;

public class Registerpage {
	private ActionDriver actionDriver;
	private WebDriver driver;
	private By signinBtn = By.id("signin");
	private By usernameDropdown = By.xpath("//*[@id='username']/div[1]/div[2]/div"); 
	private By usernameOptions = By.xpath("//*[contains(@id,'react-select-2-option')]");
	private By pwddropdown=By.xpath("//*[@id=\"password\"]/div/div[2]/div");
	private By passwordField = By.id("react-select-3-option-0-0");
	private By loginBtn = By.id("login-btn");

	private By welcomeuser=By.xpath("//*[@id=\"__next\"]/div/div/div[1]/div/div/div[2]/span");
	private By logout=By.xpath("//*[@id=\"signin\"]");

	private By lock=By.xpath("//h3[text()=\"Your account has been locked.\"]");
	public Registerpage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		this.actionDriver=new ActionDriver(driver);	

	} 

	public void verifyRegisterLink() {
		actionDriver.click(signinBtn);

	}

	public void selectUsername(String username) {
		actionDriver.click(usernameDropdown);
		List<WebElement> options = driver.findElements(usernameOptions);

		for (WebElement option : options) {
			if (option.getText().equalsIgnoreCase(username)) {
				option.click();
				break;
			}
		}
	}

	// Enter password
	public void enterPassword(String password) {
		actionDriver.click(pwddropdown);
		actionDriver.click(passwordField);

	}

	// Click login
	public void clickLogin() {
		actionDriver.click(loginBtn);
	}

	// Full login method
	public void login(String username, String password) {

		selectUsername(username);
		enterPassword(password);
		clickLogin();

	}

	public String welcomeUser() {
		return actionDriver.getText(welcomeuser);
	}

	public String lockuser() {
		//return actionDriver.textDisplayed(lock);
		return actionDriver.getText(lock);
	}

	public boolean isWelcomeUserVisible() {
		return !driver.findElements(welcomeuser).isEmpty();
	}

	// Safe check for Locked user
	public boolean isLockUserVisible() {
		return !driver.findElements(lock).isEmpty();
	}

	public void logout() {
		actionDriver.click(logout);

	}


}
