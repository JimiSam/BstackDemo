package com.Nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.Nopcommerce.actionDriver.ActionDriver;

public class Homepage {
	
	private ActionDriver actionDriver;
	
	private By logo=By.xpath("//*[@id=\"__next\"]/div/div/div[1]/div/div/div[1]/a/img");
	private By menus=By.xpath("//*[@id=\"__next\"]/div/div/div[1]/div/div/div[1]/div/a");
	private By pdtcount=By.xpath("//div[@class=\"shelf-item\"]");
	private By pdtname=By.xpath("//p[@class=\"shelf-item__title\"]");
	public Homepage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.actionDriver=new ActionDriver(driver);	
		
	}
	public 	String  titleverify() {
		return actionDriver.getTitle();
	}
	
	
	public boolean IsLogoDisplayed() {
		return actionDriver.textDisplayed(logo);
	}
	
	public int verifyMenus() {
		return actionDriver.topmenus(menus);
	}
	public int verifyCount() {
		return actionDriver.topmenus(pdtcount);
	}
	
	public String verifyPdtName() {
		return actionDriver.getText(pdtname);
		
	}

}
