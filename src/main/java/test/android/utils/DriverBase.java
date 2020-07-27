package test.android.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class DriverBase {

	private static AndroidDriver<AndroidElement> driver;
	
	public static AndroidDriver<AndroidElement> getDriverInstance(){
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", PropertyReader.loadPropertyfile().getProperty("platformName"));
		capabilities.setCapability("deviceName", PropertyReader.loadPropertyfile().getProperty("deviceName"));
		capabilities.setCapability("app", System.getProperty("user.dir")+PropertyReader.loadPropertyfile().getProperty("appPath"));
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("noReset", false);
		//capabilities.setCapability("fullReset", false);
		capabilities.setCapability("appPackage", PropertyReader.loadPropertyfile().getProperty("appPackage"));
		capabilities.setCapability("appActivity", PropertyReader.loadPropertyfile().getProperty("appActivity"));
		
		try {
			driver = new AndroidDriver<AndroidElement>(new URL(PropertyReader.loadPropertyfile().getProperty("AppiumURL")),capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
	}
	
	public static String captureScreenshot(String filename) throws IOException {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imgpath = System.getProperty("user.dir") + "\\screenshots\\" + filename + ".jpeg";
		File imgfile = new File(imgpath);
		FileUtils.copyFile(source, imgfile);
		return imgpath;

	}
}
