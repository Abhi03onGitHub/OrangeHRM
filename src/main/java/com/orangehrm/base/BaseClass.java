package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.LoggerManager;


public class BaseClass {

	protected static Properties prop;
	//protected static WebDriver driver;
	//private static ActionDriver	actionDriver;
	
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
	
	@BeforeSuite

	public void loadConfig() throws IOException 
	{
		// Load the Configuration file
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src\\main\\resources\\config.properties");
		prop.load(fis);
		logger.info("config.Properties file loaded");
	}

	@BeforeMethod
	public void setup() throws IOException {
		System.out.println("Setting Up the WebDriver for " + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);
		logger.info("Webdriver Initialized and browser maximized");
		
		// Initialize the actionDriver only once
		/*if(actionDriver== null) {
			actionDriver= new ActionDriver(driver);
			logger.info("Action Driver Instance is created. "+Thread.currentThread().getId());
		}*/
		
		// Initialize the Action Driver for the current Thread
		actionDriver.set(new ActionDriver(getDriver()));	
		logger.info("Action Driver Initialized for thread: " +Thread.currentThread().getId());
	}
// Initialize the WebDriver based on browser defined in the Config.properties
	private void launchBrowser() {		
		String browser = prop.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
			//driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			logger.info("Chrome Driver Instance is created");
		} else if (browser.equalsIgnoreCase("firefox")) {
			//driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			logger.info("Firefox Driver Instance is created");
		} else if (browser.equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			logger.info("Edge Driver Instance is created");
		} else {
			throw new IllegalArgumentException("Browser is not supported" + browser);
		}
	}
	
// Implicit Wait Setup
	private void configureBrowser() {		
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		// Maximize the driver
		getDriver().manage().window().maximize();
		// Navigate to the Driver
		try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			logger.error("Failed to navigate to the URL - " + e.getMessage());
		}

	}

	@AfterMethod
	public void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				logger.error("Unable to Quit the browser: " + e.getMessage());
			}
		
		}
		logger.info("All Driver instance is closed");
		driver.remove();
		actionDriver.remove();
		//driver=null;
		//actionDriver = null;
	}
	
/*	
//Driver getter Method
	public WebDriver getDriver() {
		return driver;
	}
	
*/ 
//Properties  getter Method	
		public static Properties getProp() {
			return prop;
		}	
	
// New getter method is initialized	
// Getter Method for WebDriver
	
	public static WebDriver getDriver() {
		
		if(driver.get()==null) {
			logger.error("Web Driver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
			
		}
		return driver.get();
	}
	
// Getter Method for ActionDriver	
		public static ActionDriver getActionDriver() {
			
			if(actionDriver.get()==null) {
				logger.error("Action Driver is not initialized");
				throw new IllegalStateException("Action Driver is not initialized");
				
			}
			return actionDriver.get();
		}
		
// Driver Setter Method
		public void setDriver(ThreadLocal<WebDriver> driver) {
			this.driver = driver;
		}

//Static Wait for the Pause
	public void staticWait(int seconds) {		
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}
}
