	package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class HomePagetest extends BaseClass {
	
	private LoginPage loginpage;
	private HomePage homepage;
	

	@BeforeMethod
	public void setupPages() {
		// System.out.println("Driver in setupPages = " + getDriver());
		
		loginpage = new LoginPage(getDriver());
		homepage = new HomePage(getDriver());
		
	} 
	
	@Test
	public void verifyOrangeHRMLogo() {
		// ExtentManager.startTest(" Test Case:- Verify the OrangeHRM logo"); -- This has been implemented in Test Listeners
		ExtentManager.logStep("Entering the Username and Password");
		loginpage.login("admin", "admin123");
		staticWait(5);
		ExtentManager.logStep("Verify Logo is Visible or not");
		Assert.assertTrue(homepage.verifyOrangeHRMLogo(),"Logo is not visible");
		ExtentManager.logStep("Validation Successful");
		ExtentManager.logStep("Logged Out Successfully!!!");
		staticWait(5);
	}
	

}
