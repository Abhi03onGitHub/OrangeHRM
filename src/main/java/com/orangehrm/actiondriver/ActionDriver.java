package com.orangehrm.actiondriver;

import java.time.Duration;
import java.util.Properties;
import com.orangehrm.base.BaseClass;

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
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int waitTime = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime)); 	
		logger.info("WebDriver Instance is created");
	}
	
// Method to Click an Element
	public void click(By by) {
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			logger.info("Clicked an element");
		} catch (Exception e) {
			System.out.println("Unable to Click the Element: " + e.getMessage());
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
			logger.info("Value Entered: "+value);
		} catch (Exception e) {
			logger.error("Unable to Enter the Text - "+e.getMessage());
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
				return true;
			} else {
				logger.info("Text is not Matching : "+ActualText+" is not equals "+ExpectedText);
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
			logger.info("Element is getting displayed");
			return driver.findElement(by).isDisplayed();
			
		} catch (Exception e) {
				logger.error("Element is not Displayed : "+e.getMessage());
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
}
