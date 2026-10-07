package com.onlinehrportal.base;

import java.io.FileInputStream;
import java.lang.reflect.Method;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.onlinehrportal.utilities.ExtentManager;
import com.onlinehrportal.utilities.LoggerManager;

public class BaseClass {
	protected static Properties prop;
	protected static WebDriver driver;
	public static final Logger logger =LogManager.getLogger(BaseClass.class);
	
@BeforeSuite
	public void loadConfig() throws IOException // map the config file
	{
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		fis.close();
		logger.info("config.properties file is loaded");
		
		//start the extent report
		
		ExtentManager.getReporter();
	}

	@BeforeMethod
	public void setup(Method method) throws IOException {
 System.out.println("Setting up webdriver for:"+this.getClass().getSimpleName());
 launchBrowser();
 configureBrowser();

 logger.info("webdriver initialized");
//Create Extent test
 ExtentManager.startTest(method.getName());

 staticWait1(3);

	}
	
	//getter method for prop
	public static Properties getprop() {
		return prop;
	}
	
	//driver getter method
	public static WebDriver getDriver()
	{
		return driver;
	}
	
	//driver setter method
	
	public void setDriver(WebDriver driver)
	{
		this.driver=driver;
	}
	
	//static wait for pause
	public void staticWait1(int  seconds)
	{
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

	public void launchBrowser() // initialize the web driver
	{
		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			ExtentManager.registerDriver(driver);
			
		}

	}

	public void configureBrowser() // configure the browser
	{
		// implicitwait
		int implicitwait = Integer.parseInt(prop.getProperty("implicitwait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitwait));

		// maximize the window
		driver.manage().window().maximize();
		// Navigate to url

		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("failed to navigate to the url"+e.getMessage());
		}
	}

//quit the browser 

	@AfterMethod
	public void teardown() {
		if (driver != null) {
			driver.quit();
			logger.info("browser is closed");
			
		}
		ExtentManager.endTest();
	}
	
	public void staticWait(int seconds)
	{
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

}
