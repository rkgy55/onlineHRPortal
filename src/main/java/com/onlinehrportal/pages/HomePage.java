package com.onlinehrportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.onlinehrportal.actiondriver.ActionDriver;

public class HomePage 
{
	private ActionDriver actionDriver;
	
	// define locators by class
	
 By adminTab=By.xpath("//span[text()=' Admin ']");
 By userIdButton=By.className("oxd-userdropdown-img");
 By logout=By.xpath("//a[text()=' Logout ']");
 
 
 //initialize the actiondriver object by passing webdriver instance
 public HomePage(WebDriver driver)
 {
 	this.actionDriver=new ActionDriver(driver);
 }
 
 //method to verify if admin tab is visible
 public boolean isAdminTabVisible()
 {
	return actionDriver.isDisplayed(adminTab);
 }
 
 //methos to verify logout
 
 public void logout() {
	 actionDriver.click(logout);
	 actionDriver.click(userIdButton);
 }

}
