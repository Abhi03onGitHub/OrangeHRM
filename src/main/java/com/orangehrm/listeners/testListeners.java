package com.orangehrm.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class testListeners implements ITestListener {
	
	
		// Trigger when a suite is started
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		// Start Logging in Extent Report
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Starting the Test "+testName);
	}

		// Trigger when a Test Succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logStepWithScreenshot(BaseClass.getDriver(),"Test Passed Successfully","Test End: " + testName +" ✅ Test Passed ");
	}

	// Trigger when a Test Failed
	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failMessage = result.getThrowable().getMessage();
		ExtentManager.logStep(failMessage);
		ExtentManager.logStepWithScreenshot(BaseClass.getDriver(),"Test Failed ","Test End: " + testName +" ❌ Test Failed ");
	}

	// Trigger when a Test is Skipped
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip("test Skipped" + testName);

	}

	
	    // Triggered when the Suite got starts
	@Override
	public void onStart(ITestContext context) {
		// Initialize the Extent Reports
		ExtentManager.getReporter();
		ITestListener.super.onStart(context);
	}
		// Triggered when the Suite got ends
	@Override
	public void onFinish(ITestContext context) {
		// Flush the Extent Reports
		ExtentManager.endTest();
	}
	

}
