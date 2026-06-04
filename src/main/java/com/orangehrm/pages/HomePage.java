package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {
	
	private ActionDriver actionDriver;

// Calling a Constructor to Initialize the values
/*	
	public HomePage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}
*/
	
// Calling a Constructor to Initialize the values from the BaseClass
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	
// Define locators Using By class	
	private By adminTab = By.xpath("//span[text()='Admin']");
	/*
	private By PIMTab = By.xpath("//span[text()='PIM']");
	private By leaveTab = By.xpath("//span[text()='Leave']");
	private By timeTab = By.xpath("//span[text()='Time']");
	private By myInfoTab = By.xpath("//span[text()='My Info']");
	private By recruitmentTab = By.xpath("//span[text()='Recruitment']");
	private By performanceTab = By.xpath("//span[text()='Performance']");
	private By dashboardTab = By.xpath("//span[text()='Dashboard']");
	private By directoryTab = By.xpath("//span[text()='Directory']");
	private By maintenanceTab = By.xpath("//span[text()='Maintenance']");
	private By claimTab = By.xpath("//span[text()='Claim']");
	private By buzzTab = By.xpath("//span[text()='Buzz']");
	*/
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logout = By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']");
	
	
// Method to verify if Admin tab is visible
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}
	
// Method to verify the OrangeHRM Logo
	public boolean verifyOrangeHRMLogo() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}
	
// Method to perform LogOut		
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logout);
	}
	
	
}		
				