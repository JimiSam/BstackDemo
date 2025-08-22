package com.Nopcommerce.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.Nopcommerce.base.Baseclass;
import com.Nopcommerce.pages.ProductPage;
import com.Nopcommerce.pages.Registerpage;
import com.Nopcommerce.utilities.ExtentManager;

public class ProductTest extends Baseclass{
	private ProductPage pdtpage;
	private Registerpage regpage;
	@BeforeMethod
	public void setuppages(Method method) {
		String testName = method.getName();   // <-- This gives your @Test method name
		System.out.println("Running Test: " + testName);

		ExtentManager.startTest(testName);
		pdtpage=new ProductPage(getDriver());
		regpage=new Registerpage(getDriver());
	}
	@Test(priority=1)
	public void verifyProductname() {
		pdtpage.verifyCount();
		List<WebElement> pdt=pdtpage.verifyPdtName();
		for(WebElement pdts:pdt) {
			System.out.println(pdts.getText());
			ExtentManager.getTest().pass("Product Names: "+pdts.getText());
		}

	}

	@Test(priority=2)
	public void verifyWishlistwithoutsignin() {
		pdtpage.verifyWishlist();
		String crnturl=getDriver().getCurrentUrl();
		System.out.println(crnturl);
		if(getDriver().getCurrentUrl().equals("https://bstackdemo.com/signin")) {
			ExtentManager.getTest().fail("Able to click on Wishlist w/o signin");

		}
		else {
			ExtentManager.getTest().pass("Unable to click on Wishlist w/o signin");
		}
	}

	@Test(priority=3)
	public void verifyWishlistwithsignin() throws InterruptedException {
		regpage.verifyRegisterLink();

		regpage.login("demouser",  "testingisfun99");
		List<WebElement> products = pdtpage.verifyPdtName();  
		List<String> expectedProducts = new ArrayList<>();

		// Step 2: Click wishlist for each product & store product name
		List<WebElement> wishlistButtons = pdtpage.verifyWishlist();
		for (int i = 0; i < wishlistButtons.size(); i++) {
			String expectedProduct = products.get(i).getText();  // get product title
			expectedProducts.add(expectedProduct);
			Thread.sleep(1000);
			wishlistButtons.get(i).click();  // click wishlist
			ExtentManager.getTest().pass("Clicked Wishlist for product: " + expectedProduct);
		}


	}
	@Test(priority=4)
	public void wishlistItemshow() throws InterruptedException {
		regpage.verifyRegisterLink();
		regpage.login("demouser",  "testingisfun99");
		// Step 1: Get product names
		List<WebElement> products = pdtpage.verifyPdtName();  
		List<String> expectedProducts = new ArrayList<>();

		// Step 2: Click wishlist for each product & store product name
		List<WebElement> wishlistButtons = pdtpage.verifyWishlist();
		for (int i = 0; i < wishlistButtons.size(); i++) {
			String expectedProduct = products.get(i).getText();  // get product title
			expectedProducts.add(expectedProduct);
			Thread.sleep(1000);
			wishlistButtons.get(i).click();  // click wishlist
			ExtentManager.getTest().pass("Clicked Wishlist for product: " + expectedProduct);
		}

		// Step 3: Go to wishlist section
		pdtpage.wishlistDisplay();

		// Step 4: Fetch wishlist products
		List<WebElement> wishlistItems = pdtpage.getWishlistProducts();

		List<String> actualProducts = wishlistItems.stream()
				.map(e -> e.getText().trim())
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toList());
		System.out.println(actualProducts);
		// Step 5: Compare expected vs actual
		for (String product : expectedProducts) {
			if (actualProducts.contains(product)) {
				ExtentManager.getTest().pass("Product found in Wishlist : " + product);
			} else {
				ExtentManager.getTest().fail("Product NOT found in Wishlist : " + product);
			}
		}
	}
	@Test(priority=5)
	public void verifyFilter() throws InterruptedException {
		regpage.verifyRegisterLink();
		regpage.login("demouser",  "testingisfun99");
		pdtpage.verifyfilter();
		Thread.sleep(2000);
		List<Double> ascprices = pdtpage.getProductPrices();
	    System.out.println("Prices after filter: " + ascprices);

	    Assert.assertTrue(pdtpage.isAscendingOrder(ascprices),
	            "Prices are NOT in ascending order! Actual: " + ascprices);
		pdtpage.verifyfilterHL();
		List<Double> descprices = pdtpage.getProductPrices();
	    System.out.println("Prices after filter: " + descprices);

	    Assert.assertTrue(pdtpage.isDescendingOrder(descprices),
	            "Prices are NOT in descending order! Actual: " + descprices);
		
	}
	@Test(priority=6)
	public void vendorDetails() throws InterruptedException {
		Map<String, String> vendorKeywords = new HashMap<>();
	    vendorKeywords.put("Apple", "iphone");
	    vendorKeywords.put("Samsung", "galaxy");
	    vendorKeywords.put("OnePlus", "oneplus");
	    vendorKeywords.put("Google", "pixel");

	    // loop through vendors
	    for (String vendorName : vendorKeywords.keySet()) {
	        System.out.println("Checking vendor: " + vendorName);

	        // click vendor
	        pdtpage.vendorClick(vendorName);
	        Thread.sleep(2000);

	        // get products
	        List<WebElement> products = pdtpage.verifyvendorPdtName();
	        for (WebElement product : products) {
	            String productName = product.getText();
	            System.out.println("Product: " + productName);

	            if (productName.toLowerCase().replace(" ", "").contains(vendorKeywords.get(vendorName))) {
	                ExtentManager.getTest().pass(vendorName + " product found: " + productName);
	            } else {
	                ExtentManager.getTest().info(vendorName + " other product: " + productName);
	            }
	        }
	        Thread.sleep(2000);
	        pdtpage.vendorClick(vendorName);
	        
	    }
		
	}
	
	@AfterMethod
	public void tearDown() {
		// Flush the report
		ExtentManager.endTest();
	}
}
