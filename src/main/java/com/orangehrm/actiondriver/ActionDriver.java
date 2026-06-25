package com.orangehrm.actiondriver;

import java.time.Duration;
import java.util.Properties;
import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Logger;

public class ActionDriver {
	private WebDriver driver;
	private WebDriverWait wait;
	protected static Properties prop;
	public static final Logger logger = LoggerManager.getLogger(ActionDriver.class);

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int waitTime = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime)); 	
		logger.info("WebDriver Instance is created");
	}
	
// Method to Click an Element
	public void click(By by) {
		String elementDescription = getElementDescription(by);
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			ExtentManager.logStep(" Clicked an Element: "+elementDescription);
		} catch (Exception e) {
			System.out.println("Unable to Click the Element: " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver()," Unable to click the element: ", elementDescription+ "_unable to click");
			logger.error("Unable to Click element");
		}
	}
	
// Method to enter the text in the input field	
	public void EnterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(value);
			logger.info("Value Entered on: "+getElementDescription(by)+" " +value);
			ExtentManager.logStep(" Entered the Text: "+value);
		} catch (Exception e) {
			logger.error("Unable to Enter the Text - "+e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver()," Unable to Enter the Text: ","text_not_entered");
		} 
	}

// Method to get the Text to the Input Field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			logger.info("Text is getting entered into the Input Field");
			return driver.findElement(by).getText();
			
		} catch (Exception e) {
			logger.error("Unable to get the Input Text "+e.getMessage());
			return ""; 
		}
	}
	
// Method to Compare the two	 text  -- Change the Return Type
	public boolean CompareText(By by, String ExpectedText) {
		try {
			waitForElementToBeVisible(by);
			String ActualText = driver.findElement(by).getText();
			if(ExpectedText.equals(ActualText)) {
				logger.info("Text is Matching : "+ActualText+" equals "+ExpectedText);
				ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), " Comapre text ", "Text Verified Successfully!! "+ActualText+" equals "+ExpectedText);
				return true;
			} else {
				logger.info("Text is not Matching : "+ActualText+" is not equals "+ExpectedText);
				ExtentManager.logFailure(BaseClass.getDriver()," Comapre text ", "Text Comparision Failed "+ActualText+" not equals to "+ExpectedText);
				return false;
			}
		} catch (Exception e) {
			logger.error("Unable to Compare the Text : "+e.getMessage());
		}
		return false;
	}

// Method to Scroll the Element
	
	public void ScrollToElement(By by) {
		try {
			waitForElementToBeVisible(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			logger.error("Unable to locate the element "+e.getMessage());
		}
		
	}
	
// Method to check the element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			boolean isDisplayed = driver.findElement(by).isDisplayed();
			ExtentManager.logStep(" Element is Displayed: ");
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Element is Displayed:", "Element is Displayed:"+getElementDescription(by));
			return driver.findElement(by).isDisplayed();
			
		} catch (Exception e) {
				logger.error("Element is not Displayed : "+e.getMessage());
				ExtentManager.logFailure(BaseClass.getDriver()," Element is not displayed ","Element is not displayed: " +getElementDescription(by));
				return false;	
		}
		
	}
	
// Wait for the Page to Load
	
	public void waitForPageLoad(int timeOutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver -> ((JavascriptExecutor) WebDriver))
			.executeScript("return docment.readyState").equals("complete");
			logger.info("Page Loaded Successfully...");
		} catch (Exception e) {
			logger.error("Page did not get loaded within "+timeOutInSec+"Seconds. Exception - "+e.getMessage());
		}
	}
	
// Wait for the Element to be Clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
			logger.info("Element is Clickable");
		} catch (Exception e) {
			logger.error("Element is not Clickable: " + e.getMessage());
		}

	}

// Wait for the Element to be Visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			logger.info("Element is Visible");
		} catch (Exception e) {
			logger.error("Element is not Visible: " + e.getMessage());
		}

	}
	
// Method to get the Element Description by the Locator
	public String getElementDescription(By locator) {
		// Check for the NULL Driver or Locator to avoid NULL Pointer exception
		if(driver==null)
			return "driver is Null";
		if(locator==null)
			return "Locator is Null";	
		
		// Find the element using the Locator
		WebElement element = driver.findElement(locator);
		
			try {
				//Get Element Description
				String name = element.getDomAttribute("name");
				String id = element.getDomAttribute("id");
				String text = element.getText();	
				String classname = element.getDomAttribute("class");
				String placeholder = element.getDomAttribute("placeholder");
				
				// Return the Description the based on the Element Attributes
				if(isNotEmpty(name)) {
					return "	 Element with Name: " + name;
				}else if(isNotEmpty(id)) {
					return " Element with Id: " + id;
				}else if(isNotEmpty(text)) {
					return " Element with Text: " + truncate(text,50);
				} else if(isNotEmpty(placeholder)) {
					return " Element with PlaceHolder: " + placeholder;
				}else if(isNotEmpty(classname)) {
					return " Element with classname: " + classname;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Unable to describe the Element - " + e.getMessage());
			}
			return "Unable to describe the Element ";
			
	}
	
// Utility method to check a string is empty or not
	
	private boolean isNotEmpty(String value) {
		return value!=null && !value.isEmpty();
	}
	
// Utility Method to truncate the long string
	
	private 	String truncate(String value, int MaxLength) {
		if(value ==null||value.length()<=MaxLength) {
			return value;}
		return value.substring(0, MaxLength)+"...";
	}
	
}
