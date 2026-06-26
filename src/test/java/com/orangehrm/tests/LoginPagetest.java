package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

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
		
		// ExtentManager.startTest(" Test Case:- Verify the Valid Login Test");  -- This has been implemented in Test Listeners
		System.out.println(" Running testMethod1 on Thread: "+Thread.currentThread().getId());
		ExtentManager.logStep("Entering the Username and Password");
		loginpage.login("admin", "admin123");
		staticWait(10);
		ExtentManager.logStep("Verifying Admin Tab is visible or Not");
		Assert.assertTrue(homepage.isAdminTabVisible(),"Admin Tab should be visible after successful login");
		ExtentManager.logStep("Validation Successful");
		homepage.logout();
		ExtentManager.logStep("Logged Out Successfully!!!");
		staticWait(5);
		
	}
	
	@Test
	public void invalidLoginTest() {
		ExtentManager.startTest(" Test Case:- Verify the Invalid Login Test");
		System.out.println(" Running testMethod1 on Thread: "+Thread.currentThread().getId());
		ExtentManager.logStep("Navigating to the Login Page");
		loginpage.login("admin", "asda123");
		ExtentManager.logStep("Entering Invalid Username and Password");
		staticWait(10);
		String ExpectedErrorMessage = "Invalid credentials";
		Assert.assertTrue(loginpage.verifyErrorMessage(ExpectedErrorMessage),"Test Failed: Error Message");
		ExtentManager.logStep("Validation Successful");
		ExtentManager.logStep("Logged Out Successfully!!!");
		staticWait(5);
		
	}

}
