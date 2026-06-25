package com.orangehrm.tests;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyTest extends BaseClass {
	
	@Test
	public void test() {
		ExtentManager.startTest(" Test Case:- Dummy Test - Verify the Title");
		String title = getDriver().getTitle();
		ExtentManager.logStep("Verifying the Title");
		assert title.equals("OrangeHRM"):"Test Failed - Title is not matchiing";
		
		System.out.println("Test Passed - Title is matching");
		ExtentManager.logSkip("This Case is Skipped");
		throw new SkipException("Skiping the Test as part of test");
	}

}
	