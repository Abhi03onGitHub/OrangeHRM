package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

public class LoginPagetest extends BaseClass {
	
	private LoginPage loginpage;
	private HomePage homepage;
	

	@BeforeMethod
	public void setupPages() {
		// System.out.println("Driver in setupPages = " + getDriver());
		
		loginpage = new LoginPage(getDriver());
		homepage = new HomePage(getDriver());
		
	}  
	
	@Test	
	public void verifyValidLogin() {
		loginpage.login("admin", "admin123");
		staticWait(10);
		Assert.assertTrue(homepage.isAdminTabVisible(),"Admin Tab should be visible after successful login");
		homepage.logout();
		staticWait(5);
	}
	
	@Test
	public void invalidLoginTest() {
		loginpage.login("admin", "asda123");
		staticWait(10);
		String ExpectedErrorMessage = "Invalid credentials1";
		Assert.assertTrue(loginpage.verifyErrorMessage(ExpectedErrorMessage),"Test Failed: Error Message");
		staticWait(5);
	}

}
