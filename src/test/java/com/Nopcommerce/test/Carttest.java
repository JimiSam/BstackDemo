package com.Nopcommerce.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Nopcommerce.base.Baseclass;
import com.Nopcommerce.pages.Cartpage;
import com.Nopcommerce.pages.ProductPage;
import com.Nopcommerce.pages.Registerpage;
import com.Nopcommerce.utilities.ExtentManager;

public class Carttest extends Baseclass{
	private ProductPage pdtpage;
	private Registerpage regpage;
	private Cartpage cartpage;
	@BeforeMethod
	public void setuppages(Method method) {
		String testName = method.getName();   // <-- This gives your @Test method name
		System.out.println("Running Test: " + testName);

		ExtentManager.startTest(testName);
		pdtpage=new ProductPage(getDriver());
		regpage=new Registerpage(getDriver());
		cartpage=new Cartpage(getDriver());
	}

	@Test(priority=1)
	public void addToCartButton() {
		Boolean addtoCartdisplay=cartpage.verifyAddtoCart();
		if(addtoCartdisplay) {
			ExtentManager.getTest().pass("Add to Cart button displayed");
		}
		else {
			ExtentManager.getTest().fail("Add to Cart button missing");
		}
	}

	@Test(priority=2)
	public void addToCartButtonwoSignin() throws InterruptedException {
		cartpage.verifyAddtoCartClick();
		Thread.sleep(2000);
		String crnturl=getDriver().getCurrentUrl();
		System.out.println(crnturl);
		if(crnturl.equals("https://bstackdemo.com/signin?checkout=true")) {
			ExtentManager.getTest().pass("Add to cart not working without signin");
		}else {
			ExtentManager.getTest().fail("Add to cart without signin occurs");
		}
	}
	@Test(priority=3)
	public void addToCartButtonwithSignin() throws InterruptedException {
		regpage.verifyRegisterLink();

		regpage.login("demouser",  "testingisfun99");
		cartpage.verifyAddtoCartClick();
		Thread.sleep(2000);
		String crnturl=getDriver().getCurrentUrl();
		System.out.println(crnturl);
		if(crnturl.equals("https://bstackdemo.com/checkout")) {
			Thread.sleep(1000);
			ExtentManager.getTest().pass("Add to cart working with signin");
		}else {
			ExtentManager.getTest().fail("Add to cart fail");
		}
	}

	@Test(priority=4)
	public void addToCartButtonwithSigninpdt() throws InterruptedException  {
		regpage.verifyRegisterLink();

		regpage.login("demouser",  "testingisfun99");
		cartpage.verifyAddtoCartClick();
		cartpage.verifyAddressDetails("demo", "user", "demooooo", "demo", "1324768");
		String confirmmsg=cartpage.verifyOrderConfirmation();
		System.out.println(confirmmsg);
		String orderid=cartpage.verifyOrderId();
		System.out.println(orderid);
		cartpage.verifyInvoiceDownload();
		String crnturl=getDriver().getCurrentUrl();
		System.out.println(crnturl);
		if(crnturl.equals("https://bstackdemo.com/")) {
			Thread.sleep(1000);
			ExtentManager.getTest().pass("Add to cart working with signin");
		}else {
			ExtentManager.getTest().fail("Add to cart fail");
		}
	}

	@Test(priority=5)
	public void addToCartButtonwithSigninforAll() throws InterruptedException  {
		regpage.verifyRegisterLink();
		regpage.login("demouser",  "testingisfun99");
		List<WebElement> products = pdtpage.verifyPdtName();  
		List<String> expectedProducts = new ArrayList<>();
		int pdtcount=pdtpage.verifyCount();
		// Step 2: Click addtocart for each product & store product name
		List<WebElement> addtocart = cartpage.verifyAddtoCartAll();
		for (int i = 0; i < addtocart.size(); i++) {
			String expectedProduct = products.get(i).getText();  // get product title
			expectedProducts.add(expectedProduct);
			Thread.sleep(1000);
			addtocart.get(i).click();  // click addtocart
			Thread.sleep(1000);
			cartpage.verifyclose();
			ExtentManager.getTest().pass("Clicked Add to Cart for product: " + expectedProduct);

		}
		cartpage.verifyCartIcon();

		System.out.println(pdtcount);
		if(pdtcount==25) {
			ExtentManager.getTest().pass("Clicked Add to Cart for All: " + pdtcount +"products");
		}else {
			ExtentManager.getTest().fail("All products not added");
		}

	}
	@Test(priority=6)
	public void quantityAddProduct() throws InterruptedException  {
		regpage.verifyRegisterLink();
		regpage.login("demouser",  "testingisfun99");
		cartpage.verifyAddtoCartClickpdt();
		Thread.sleep(1000);
		cartpage.verifyquantityplusClick();
		ExtentManager.getTest().pass("Successfully clicked");

	}
	@Test(priority=7)
	public void quantityMinusProduct() throws InterruptedException  {
		regpage.verifyRegisterLink();
		regpage.login("demouser",  "testingisfun99");
		cartpage.verifyAddtoCartClickpdt();
		Thread.sleep(1000);
		int qval = Integer.parseInt(cartpage.verifyquantityvalue());
		if(qval==1) {
			cartpage.verifyquantityplusClick();
			ExtentManager.getTest().pass("Quantity was 1, so increased instead of decreasing");
		}else {
			cartpage.verifyquantityminusClick();
			ExtentManager.getTest().pass("Successfully decreased product quantity");
		}


	}
	@Test(priority=8)
	public void removeitems() throws InterruptedException  {
		regpage.verifyRegisterLink();
		regpage.login("demouser",  "testingisfun99");
		cartpage.verifyAddtoCartClickpdt();
		Thread.sleep(1000);

		String msg=cartpage.verifyremoveitem();
		if(msg.contains("Add some products in the bag ")) {
			ExtentManager.getTest().pass("Successfully removed");
		}
		else {
			ExtentManager.getTest().pass("Unable to  remove");
		}

	}

	@Test(priority = 8)
	public void validateCartTotal() throws InterruptedException {

		// Login before checking cart
		regpage.verifyRegisterLink();
		regpage.login("demouser", "testingisfun99");

		// Wait for page to load
		Thread.sleep(2000);

		// Step 1: Get product elements
		List<WebElement> products = pdtpage.verifyPdtName();  
		List<String> expectedProducts = new ArrayList<>(); 
		List<WebElement> addtocart = cartpage.verifyAddtoCartAll();
		// Ensure we don’t go out of bounds
		int count = Math.min(products.size(), addtocart.size());

		// Step 3: Add products to cart
		for (int i = 0; i < count; i++) {
			String expectedProduct = products.get(i).getText();
			expectedProducts.add(expectedProduct);

			Thread.sleep(1000);
			addtocart.get(i).click();  // click Add to Cart
			Thread.sleep(1000);

			cartpage.verifyclose();
			ExtentManager.getTest().pass("Clicked Add to Cart for product: " + expectedProduct);
		}

		// Step 4: Navigate to Cart Page
		cartpage.verifyCartIcon();   // ✅ Make sure you have a method that clicks on the cart icon/button

		// Step 5: Get prices & quantities from Cart
		List<Double> prices = cartpage.getProductPrices();
		List<Integer> quantities = cartpage.getProductQuantities();

		//    System.out.println("Prices: " + prices);
		//    System.out.println("Quantities: " + quantities);

		// Step 6: Calculate expected subtotal
		double expectedTotal = 0.0;
		for (int i = 0; i < Math.min(prices.size(), quantities.size()); i++) {
			expectedTotal += prices.get(i) * quantities.get(i);
		}

		// Step 7: Get actual subtotal displayed
		double actualTotal = cartpage.getDisplayedSubtotal();
		System.out.println("Expected Total: " + expectedTotal + ", Actual Total: " + actualTotal);

		// Step 8: Assertion
		Assert.assertEquals(actualTotal, expectedTotal, "Cart total price mismatch!");
		ExtentManager.getTest().pass(actualTotal+" equals "+expectedTotal);
	}



	@AfterMethod
	public void tearDown() {
		// Flush the report
		ExtentManager.endTest();
	}
}
