package com.onlinehrportal.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager 
{

private static ExtentReports extent;
private static ThreadLocal<ExtentTest> test =new ThreadLocal<>();
private static Map<Long,WebDriver> driverMap =new HashMap<>();

//initialize the extent report 
public synchronized static ExtentReports getReporter()
{
	if (extent==null)
	{
	String	reportPath=System.getProperty("user.dir")+"/src/test/resources/extent-reports/ExtentReport.html";
	ExtentSparkReporter spark =new ExtentSparkReporter(reportPath);
	spark.config().setReportName("automation-test-report");
	spark.config().setDocumentTitle("orangeHRM Report");
	spark.config().setTheme(Theme.DARK);
	extent=new ExtentReports();
	extent.attachReporter(spark);
	
	}
	return extent;
}

//start the test
public synchronized static ExtentTest startTest(String testName)
{
	ExtentTest extentTest =getReporter().createTest(testName);
	
	test.set(extentTest);
	return extentTest;
}

//end the test 
public synchronized static void endTest()
{
	getReporter().flush();
}


//get current thread test name

public synchronized static ExtentTest getTest()
{
	return test.get();
}

//method to get the name of the current test
public static String  getTestName()
{
	ExtentTest currentTest=getTest();
	if(currentTest!=null)
	{
		return currentTest.getModel().getName();
	}
	else
	{
		return "No test is currently active for this thread";
	}
	
}

//log a step 

public static void logstep(String logMessage)
{
	getTest().info(logMessage);
}

//log a step with a SS

public static void logStepWithScreenShot(WebDriver driver, String logMessage,String screenShotMessage)
{
	getTest().pass(logMessage);
	attachScreenshot(driver,screenShotMessage);
}

//log a failure 

public static void logFailure (WebDriver driver, String logMessage,String screenShotMessage)
{
	getTest().fail(logMessage);
	attachScreenshot(driver,screenShotMessage);
}


//log a skip

public static void logSkip(String logMessage) 
{
	

		getTest().skip(logMessage);
	}

// take a screen shot with date and time

public static String takeScreenshot(WebDriver driver,String  screenShotName)
{
	TakesScreenshot ts=((TakesScreenshot)driver);
	File src=ts.getScreenshotAs(OutputType.FILE);
	
	//format date and time for file name
	String timestamp=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
	
	//saving  the screen shot to a file
	

	String destPath=System.getProperty("user.dir")+"/src/test/resources/extent-reports/screenshots"+screenShotName+"_"+timestamp+".png";
File finalPath=	new File (destPath);
try {
	FileUtils.copyFile(src, finalPath);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
//convert ss to based64 fir embedding  in the report 
String base64Format=converttoBase64(src);
return base64Format;
}


//convert  screenshot to Base64 format 

public static String converttoBase64(File screenShotFile)
{
	String base64Format="";
	//read the file content into a byte array
	byte[] fileContent;
	try {
		 fileContent= FileUtils.readFileToByteArray(screenShotFile);
		 base64Format=Base64.getEncoder().encodeToString(fileContent);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//convert the byte array to base64 string 
	return base64Format;
	
}

// attach  ss to report using base 64

public synchronized static  void attachScreenshot(WebDriver  driver,String message)
{
	try {
		String screenShotBase64=takeScreenshot(driver,getTestName());
		getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotBase64).build());
	} catch (Exception e) {
		getTest().fail("failed to attach screenshot"+message);
		e.printStackTrace();
	}
	
}

//register webdriver  for current thread

public static void registerDriver(WebDriver driver)
{
	driverMap.put(Thread.currentThread().getId(),driver);
}
}

