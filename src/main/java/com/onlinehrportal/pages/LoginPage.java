package com.onlinehrportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.onlinehrportal.actiondriver.ActionDriver;

public class LoginPage 
{
private ActionDriver actionDriver;

//define locators using by class

By userNameField=By.name("username");
By passwordField=By.name("password");
By loginButton=By.xpath("//button[text()=' Login ']");
By errorMessage=By.xpath("//p[text()='Invalid credentials']");

public LoginPage(WebDriver driver)
{
	this.actionDriver=new ActionDriver(driver);
}



//method to perform login

public void login(String userName, String password)
{
	actionDriver.enterText(userNameField,userName);
	actionDriver.enterText(passwordField,password);
	actionDriver.click(loginButton);
}
//method to check if error is displayed

public boolean  isTheErrorMessageIsDisplayed()
{
	return actionDriver.isDisplayed(errorMessage);
}
//method to get the text from error message

public String getErrorMessageText()
{
	return actionDriver.getText(errorMessage);
	
}
public boolean verifyErrorMessage(String expectedError)
{
	return actionDriver.compareText(errorMessage,expectedError);
}

}
