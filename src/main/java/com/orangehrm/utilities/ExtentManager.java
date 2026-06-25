package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> drivermap = new HashMap<>();

	// Initialize the Extent Report

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/src/test/resources/ExtentReport/ExtentReport.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("Automation Test Report");
			spark.config().setDocumentTitle("OrangeHRM Report");
			spark.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.attachReporter(spark);
			// Adding System Information
			extent.setSystemInfo("Operating System ", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version ", System.getProperty("java.version"));
			extent.setSystemInfo("User Name ", System.getProperty("user.name"));

		}
		return extent;
	}

	// Start the test
	public synchronized static ExtentTest startTest(String testName) {
		ExtentTest extentTest = getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}
	
	
	// End the Test
	public synchronized static void endTest() {
		getReporter().flush();
	}
	
	
	// Get the Current Thread's test
	public synchronized static ExtentTest getTest() {
		return test.get();
	}
	
	// Method to get the name of the current test
	public static String getTestName() {
		ExtentTest currentTest = getTest();
		if(currentTest!=null) {
			return currentTest.getModel().getName();
		}else {
			return "No Test is currently Active for this Thread";
		}
	}
	
	// Log a Step
	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}
	

	// Log a Step Validation with Screenshots
	public static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenshotMessage) {
		getTest().pass(logMessage);
		// Screenshot Method
		attachScreenshot(driver,screenshotMessage);
	}
	
	// Log a Failure
	public static void logFailure(WebDriver driver, String logMessage, String screenshotMessage) {
		String colorMessage = String.format("<span style='color:red'>%s</span>", logMessage);
		getTest().fail(colorMessage);
		// Screenshot Method
	}
	
	// Log a Skip 
	public static void logSkip(String logMessage) {
		String colorMessage = String.format("<span style='color:orange'>%s</span>", logMessage);
		getTest().skip(logMessage);
		// Screenshot Method
	}
	
	// Take a Screenshot with Date and Time in the file
	public synchronized static String takeScreenshot(WebDriver driver,String screenshotName) {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		screenshotName = screenshotName.replaceAll("[\\\\/:*?\"<>|]", "_");
		// Date and Time Format for File Name
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		
		// Saving the Screenshot to the file
		String destPath = System.getProperty("user.dir") + "/src/test/resources/screenshots/"+screenshotName+"_"+timestamp+".png";
		File finalPath = new File(destPath);
		try {
			System.out.println("Screenshot Name = " + screenshotName);
			System.out.println("Destination Path = " + destPath);
			FileUtils.copyFile(src, finalPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Convert Screenshot into base64 fir embedding into the report
		String base64Format = convertToBase64(src);
		return base64Format;
	}
	
	// Convert Screenshot into the base64 Format
	public static String convertToBase64(File ScreenShotFile) {
		String base64Format = "";
		// Read the file content into the byte array
		
		try {
			byte[] fileContent = FileUtils.readFileToByteArray(ScreenShotFile);
			base64Format = Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Convert to the byte to Base64 String
		return base64Format;	
	} 
	
	// Attach screenshot to report using base64
	public synchronized static void attachScreenshot(WebDriver driver, String message) {
		try {
			String screenshotBase64 = takeScreenshot(driver,	getTestName());
			getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
		} catch (Exception e) {
			getTest().fail(" Failed to attach the Screenshots: "+message);
			e.printStackTrace();
		}  
	}
	
	// Register Web Driver for current thread

	public static void registerDriver(WebDriver driver) {
		drivermap.put(Thread.currentThread().getId(), driver);
	}

}
