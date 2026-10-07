package com.onlinehrportal.tests;

import org.testng.annotations.Test;

import com.onlinehrportal.base.BaseClass;

public class Tests extends BaseClass
{
	@Test
public void Testss()
{
	String title=driver.getTitle();
	assert title.equals("OrangeHRM"):"Test Failed";
	logger.info("Test Passed-title is matching");
}
}

