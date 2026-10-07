package com.onlinehrportal.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.onlinehrportal.base.BaseClass;
import com.onlinehrportal.utilities.ExtentManager;

public class ActionDriver 
{
private static WebDriver driver;
private WebDriverWait wait;
public static final Logger logger=BaseClass.logger;


public ActionDriver(WebDriver driver)
{
	this.driver=driver;
	this.wait=new WebDriverWait(driver,Duration.ofSeconds(30));
	logger.info("webdriver instance is created");
	
}

//to click an element

public void click(By by)
{
	try {
		waitForElementToBeClickable(by);
		driver.findElement(by).click();
		ExtentManager.logstep("clicked an element");;
		logger.info("clicked an element");
	} catch (Exception e) {
		ExtentManager.logFailure(driver,"unable to click an element","");
		logger.error("unable to click the element"+e.getMessage());
	}
}

//method to check if an element is displayed

public boolean  isDisplayed(By by) 
{
	try {
		waitForElementToBeVisible( by);
boolean isDisplayed=	driver.findElement(by).isDisplayed();
if(isDisplayed)
{
		logger.info("element is visible");
		ExtentManager.logFailure(driver,"element is not displayed","");
		 return isDisplayed;
}
else
{
		return isDisplayed;
}

	} catch (Exception e) {
	
		logger.error("element is not displayed"+e.getMessage());
	}
	return false;
}

//method to enter text into an input file

public void enterText(By by,String value)
{
	 try {
		waitForElementToBeVisible( by);
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(value);
	 } catch (Exception e) {
		
		System.out.println("unable to enter the value"+e.getMessage());
	 }
}

//method to get text from an input field

public String getText(By by)
{
	try {
		waitForElementToBeVisible( by);
return driver.findElement(by).getText();
	} catch (Exception e) {
		
	System.out.println("unable to get the text"+e.getMessage());
	}
	return "";
}

//wait for the element to be clickable

public void waitForElementToBeClickable(By by)
{
	try {
		wait.until(ExpectedConditions.elementToBeClickable(by));
	} catch (Exception e) {
		
	System.out.println("element is not clickable"+e.getMessage());
	}
}

//scroll to an element

public void scrollToElement(By by) {
	try {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		WebElement element=driver.findElement(by);
		js.executeScript("arguments[0],scrollIntoView(true)",element);
	} catch (Exception e) {
		System.out.println("unable to scroll to an element"+e.getMessage());
	}
	
}

// wait for the element to be visible

public void waitForElementToBeVisible(By by) 
{
	try {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	} catch (Exception e) {
		
		System.out.println("element is not visible"+e.getMessage());
	}
}
public boolean compareText(By by,String expectedText)
{
	String actualText=driver.findElement(by).getText();
	if(expectedText.equals(actualText))
{
		ExtentManager.logStepWithScreenShot(driver,"compare text","text verified successfully"+ actualText +"equals"+ expectedText);
	System.out.println("Text are Matching::"+actualText+ "equals" +expectedText );
}
else
{
	System.out.println("Texts are not matching::"+actualText+ " notequals"+expectedText);
}
	return false;
}





}
