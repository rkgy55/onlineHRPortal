package com.onlinehrportal.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.onlinehrportal.base.BaseClass;
import com.onlinehrportal.pages.HomePage;
import com.onlinehrportal.pages.LoginPage;
import com.onlinehrportal.utilities.ExtentManager;

public class LoginPageTest  extends BaseClass // to get the driver details we are extending base class
{
private LoginPage loginpage;
private HomePage homepage;


@BeforeMethod
public void setupPages()
{
	loginpage=new LoginPage(getDriver());
	homepage=new HomePage(getDriver());
	
}



public void verifyValidLoginTest() 
{
	ExtentManager.startTest("verify login test");
    loginpage.login("admin", "admin123");
    ExtentManager.logstep("logged in successfully");
    ExtentManager.logstep("navigating to login page  entering username and password");
    
    // Assert real page state instead of hardcoded 'false'
   Assert.assertTrue(homepage.isAdminTabVisible());
    homepage.logout();
    ExtentManager.logstep("logged out successfully");
    staticWait(2);
}
@Test
public void verifyInvalidLoginTest()
{
	loginpage.login("admin", "admin12");
	String expectedErrorMessage="Invalid credentials";
	Assert.assertTrue(loginpage.verifyErrorMessage(expectedErrorMessage),"test failed:: invalid error message");
	logger.error("this has errored out");
}
}
