package com.orangehrm.tests;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;

public class DummyTest extends BaseClass {
	
	@Test
	public void test() {
		String title = driver.getTitle();
		assert title.equals("OrangeHRM"):"Test Failed - Title is not matchiing";
		
		System.out.println("Test Passed - Title is matching");
	}

}
	