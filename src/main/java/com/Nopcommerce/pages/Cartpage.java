package com.Nopcommerce.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Nopcommerce.actionDriver.ActionDriver;

public class Cartpage {
	private ActionDriver actionDriver;
	private WebDriver driver;
	private By addtoCart=By.xpath("//div[@class=\"shelf-item__buy-btn\"]");
	private By checkout=By.xpath("//div[text()=\"Checkout\"]");
	private By firstname=By.id("firstNameInput");
	private By lastname=By.id("lastNameInput");
	private By address=By.id("addressLine1Input");
	private By state=By.id("provinceInput");
	private By postal=By.id("postCodeInput");
	private By submit=By.id("checkout-shipping-continue");
	private By confirmmsg=By.id("confirmation-message");
	private By orderid=By.xpath("//*[@id=\"checkout-app\"]/div/div/div/ol/li/div/div/div[2]");
	private By invoice=By.id("downloadpdf");
	private By continueshop=By.xpath("//button[text()=\"Continue Shopping »\"]");
	private By close=By.xpath("//div[@class=\"float-cart__close-btn\"]");
	private By carticon=By.xpath("//span[@class=\"bag bag--float-cart-closed\"]");
	private By quantityplus=By.xpath("//button[contains(text(),'+')]");
	private By quantityminus=By.xpath("//button[contains(text(),'-')]");
	private By quantityvalue=By.xpath("//p[text()=\"Quantity: \" and text()=\"1\"]");
	private By removeitem=By.xpath("//div[@class=\"shelf-item__del\"]");
	private By msg=By.xpath("//p[@class=\"shelf-empty\"]");
	private By prices=By.xpath("//div[@class=\"shelf-item__price\"]");
	private By cartitem=By.xpath("//div[@class=\"float-cart__shelf-container\"]/child::div");
	private By subtotal=By.xpath("//p[@class=\"sub-price__val\"]");
	

	public Cartpage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		this.actionDriver=new ActionDriver(driver);
	}
	
	public boolean verifyAddtoCart() {
		actionDriver.waitelementtoBeVisible(addtoCart);
		return actionDriver.textDisplayed(addtoCart);
	}
	
	public List<WebElement> verifyAddtoCartAll() {
		return actionDriver.getMultiples(addtoCart);
		
	}
	public void verifyclose(){
		actionDriver.click(close);
	}
	public void verifyCartIcon() {
		actionDriver.click(carticon);
	}
	public void verifyAddtoCartClick() {
		actionDriver.click(addtoCart);
		actionDriver.click(checkout);
	}
	public void verifyAddtoCartClickpdt() {
		actionDriver.click(addtoCart);
	}
	
	public void verifyAddressDetails(String fname,String lname,String add,String province,String post) {
		actionDriver.enterText(firstname, fname);
		actionDriver.enterText(lastname, lname);
		actionDriver.enterText(address, add);
		actionDriver.enterText(state, province);
		actionDriver.enterText(postal, post);
		actionDriver.click(submit);
	}
	
	public String verifyOrderConfirmation() {
		return actionDriver.getText(confirmmsg);
	}
	public String verifyOrderId() {
		return actionDriver.getText(orderid);
	}
	public void verifyInvoiceDownload() {
		actionDriver.click(invoice);
		actionDriver.click(continueshop);
	}
	public void verifyquantityplusClick() {
		actionDriver.click(quantityplus);
	}
	public String verifyquantityvalue() {
		 String qtyText=actionDriver.getText(quantityvalue);
		 String numberOnly = qtyText.replaceAll("[^0-9]", ""); // "1"
		 return numberOnly;
	}
	public void verifyquantityminusClick() {
		actionDriver.getText(quantityvalue);
		actionDriver.click(quantityminus);
	}
	public String verifyremoveitem() {
		actionDriver.click(removeitem);
		return actionDriver.getText(msg);
	}
	public List<WebElement> getCartItems() {
        return driver.findElements(cartitem);
    }
	
	
	public double getDisplayedSubtotal() {
	    WebElement subtotalElement = driver.findElement(subtotal);
	    String subtotalText = subtotalElement.getText().trim();
	    subtotalText = subtotalText.replaceAll("[^0-9.]", ""); // keep only numbers and dot
	    return Double.parseDouble(subtotalText);
	}

	   // Extract product quantities
	 public List<Integer> getProductQuantities() {
		    List<Integer> quantitiesList = new ArrayList<>();
		    for (WebElement item : getCartItems()) {
		        String qtyText = item.findElement(quantityvalue).getText(); // e.g. "Quantity: 2"
		        String numberOnly = qtyText.replaceAll("[^0-9]", ""); 

		        if (!numberOnly.isEmpty()) {
		            quantitiesList.add(Integer.parseInt(numberOnly));
		        }
		    }
		    return quantitiesList;
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

	    
}
