package com.Nopcommerce.test;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Nopcommerce.base.Baseclass;
import com.Nopcommerce.pages.Registerpage;
import com.Nopcommerce.utilities.Excelutils;
import com.Nopcommerce.utilities.ExtentManager;


public class RegisterTest extends Baseclass {
	
	private Registerpage regpage;
	@BeforeMethod
	public void setuppages(Method method) {
		String testName = method.getName();   // <-- This gives your @Test method name
        System.out.println("Running Test: " + testName);

        ExtentManager.startTest(testName);
		regpage=new Registerpage(getDriver());
	}
	
	@Test(priority=1)
	public void verifyRegister() {
		regpage.verifyRegisterLink();
	}
	
	@Test(priority=2)
	public void verify_login() throws InterruptedException {
		regpage.verifyRegisterLink();
		//Reading the data from excel file by the specified path
		String xl = "C:\\Jimitha works\\automation\\signin.xlsx";
		String Sheet = "Sheet1";
		int rowCount = Excelutils.getRowCount(xl,Sheet);
		System.out.println(rowCount);


		for (int i = 1; i <= rowCount; i++) {
			int cellCount = Excelutils.getCellCount(xl, Sheet, i);
			//System.out.println("Cell Count: " + cellCount);

			// Ensure j starts at 0 and loops until cellCount  to prevent index out of bounds.
			for (int j = 0; j < cellCount; j += 2) {  // Each row should have pairs of values (username, password)
				String userName = Excelutils.getCellValue(xl, Sheet, i, j); 
				System.out.println("UserName = " + userName);

				String pwd = Excelutils.getCellValue(xl, Sheet, i, j + 1); 
				System.out.println("Password = " + pwd);
				
				regpage.login(userName,  pwd);
			//	Thread.sleep(2000);
				
				if (regpage.isLockUserVisible()) {
				    
				    String lockMsg = regpage.lockuser();
				    System.out.println(userName + " login locked -> " + lockMsg);
				    if(lockMsg.isBlank()) {
				    ExtentManager.getTest().fail(userName + " login successfull");
				    }else {
				    	ExtentManager.getTest().pass(userName + " login locked -> " + lockMsg);
				    }

				} else if (regpage.isWelcomeUserVisible()) {
				    
				    String welcome = regpage.welcomeUser();
				    System.out.println(welcome + " logged in successfully");
				    ExtentManager.getTest().pass(userName + " logged in successfully");

				    regpage.logout();

				} else {
				    
				    System.out.println(userName + " login failed (no welcome or lock message found)");
				    ExtentManager.getTest().fail(userName + " login failed");
				}

				
				getDriver().get("https://bstackdemo.com/signin");
			}
				}
			
	
		}
	@AfterMethod
    public void tearDown() {
        // Flush the report
        ExtentManager.endTest();
    }
	}