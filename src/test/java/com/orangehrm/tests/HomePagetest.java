package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

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
		loginpage.login("admin", "admin123");
		staticWait(5);
		Assert.assertTrue(homepage.verifyOrangeHRMLogo(),"Logo is not visible");
		staticWait(5);
	}
	

}
