package com.Nopcommerce.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Nopcommerce.actionDriver.ActionDriver;

public class ProductPage {
	private ActionDriver actionDriver;
	private WebDriver driver;
	private By pdtcount=By.xpath("//div[@class=\"shelf-item\"]");
	private By pdtname=By.xpath("//p[@class=\"shelf-item__title\"]");
	private By wishlist=By.xpath("//*[@id]/div[1]/button");
	private By fav=By.xpath("//*[@id=\"favourites\"]");
	private By wishlistitem=By.cssSelector(".shelf-item__title");
	private By filter=By.xpath("//*[@id=\"__next\"]/div/div/main/div[2]/div[1]/div[1]/select");
	private By prices=By.xpath("//div[@class=\"shelf-item__price\"]");
	private By vendors=By.xpath("//span[@class=\"checkmark\"]");
	private By vendorpdts=By.xpath("//p[@class=\"shelf-item__title\"]");
	public ProductPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		this.actionDriver=new ActionDriver(driver);
	}
	public int verifyCount() {
		return actionDriver.topmenus(pdtcount);
	}

	public List<WebElement> verifyPdtName() {

		return actionDriver.getMultiples(pdtname);	
	}
	public List<WebElement> verifyWishlist() {

		//actionDriver.click(wishlist);
		return actionDriver.getMultiples(wishlist);
	}

	public void wishlistDisplay() {
		actionDriver.click(fav);
	}
	public List<WebElement> getWishlistProducts() {
		return actionDriver.getMultiples(wishlistitem);

	}

	public void verifyfilter() {
		actionDriver.dropdown(filter, "Lowest to highest");

	}
	public void verifyfilterHL() {
		actionDriver.dropdown(filter, "Highest to lowest");

	}
	public List<Double> getProductPrices() {
		List<WebElement> priceElements = driver.findElements(prices);
		List<Double> prices = new ArrayList<>();

		for (WebElement el : priceElements) {
			String text = el.getText().trim();

			// Example text: "799.00\nor 9 x  88.78"
			String clean = text.split("\n")[0]   // take only first line
					.replaceAll("[^0-9.]", ""); // keep only digits and dot

			if (!clean.isEmpty()) {
				prices.add(Double.parseDouble(clean));
			}
		}
		return prices;
	}

	public boolean isAscendingOrder(List<Double> prices) {
		for (int i = 0; i < prices.size() - 1; i++) {
			if (prices.get(i) > prices.get(i + 1)) {
				return false; // Found a violation
			}
		}
		return true;
	}

	public boolean isDescendingOrder(List<Double> prices) {
		for (int i = 0; i > prices.size() - 1; i++) {
			if (prices.get(i) < prices.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

	public List<WebElement> verifyVendors() {
		return actionDriver.getMultiples(vendors);
	}


	public List<WebElement> verifyvendorPdtName() {

		return actionDriver.getMultiples(vendorpdts);	
	}
	public void vendorClick(String vendorName) {
		WebElement vendor = driver.findElement(By.xpath("//span[contains(text(),'" + vendorName + "')]"));
		vendor.click();

	}
}
