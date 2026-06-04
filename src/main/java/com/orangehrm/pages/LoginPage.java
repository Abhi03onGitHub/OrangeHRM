package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {
	private ActionDriver actionDriver;
/*	
	// Calling a Constructor to Initialize the values
	
	public LoginPage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}
*/
	
public LoginPage(WebDriver driver) {
	this.actionDriver = BaseClass.getActionDriver();
}	
	
// Define locators Using By class
	
	private By usernamefield = By.name("username");
	private By passwordfield = By.cssSelector("input[type='password']");
	private By loginbutton = By.xpath("//button[normalize-space()='Login']");
	private By errormessage = By.xpath("//p[text()='Invalid credentials']");
	private By forgotpassword = By.xpath("//p[text()='Forgot your password? ']");
	private By companylink = By.xpath("//a[text()='OrangeHRM, Inc']");
	
	
// Method to define the locatorsLogin
	
	public void login(String userName, String password) {
		actionDriver.EnterText(usernamefield, userName);
		actionDriver.EnterText(passwordfield, password);
		actionDriver.click(loginbutton);			
		}

// Method to check if error message is displayed
	
	public boolean isErrorMessageDisplayed() {
		return actionDriver.isDisplayed(errormessage);
	}
	
// Method to get the Error message text
	
	public String getErrorMessageText() {
		return actionDriver.getText(errormessage);
		
	}

// Verify if the Error message is displayed correctly
	
	public boolean verifyErrorMessage(String expectedError) {
		return actionDriver.CompareText(errormessage, expectedError);
	}
	
}