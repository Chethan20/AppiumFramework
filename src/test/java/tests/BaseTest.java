package tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import utilities.ExcelUtil;
import utilities.PropertyReader;

/**
 * @author Chethan
 *
 */
public class BaseTest {

	protected AndroidDriver<AndroidElement> driver;
	protected AppiumDriverLocalService service;
	Properties property = PropertyReader.loadPropertyfile();

	@BeforeSuite
	public void beforeSuite() {
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
	}

	@AfterSuite
	public void afterSuite() {
		if (service != null) {
			service.stop();
		}
	}

	@Parameters({"PlatformName","AutomationName"})
	@BeforeTest
	public void beforeTest(String platform, String automation) {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", platform);
		capabilities.setCapability("automationName", automation);
		capabilities.setCapability("deviceName", property.getProperty("deviceName"));
		capabilities.setCapability("app", System.getProperty("user.dir") + property.getProperty("appPath"));
		capabilities.setCapability("appPackage", property.getProperty("appPackage"));
		capabilities.setCapability("appActivity", property.getProperty("appActivity"));
		capabilities.setCapability("noReset", false);

		try {
			driver = new AndroidDriver<AndroidElement>(new URL(property.getProperty("AppiumURL")), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.startRecordingScreen(new AndroidStartScreenRecordingOptions());
	}

	@AfterTest
	public void afterTest() throws IOException {
		String base64String = driver.stopRecordingScreen();
		byte[] data = Base64.decodeBase64(base64String);
		String destinationPath = System.getProperty("user.dir") + "\\target\\video.mp4";

		Path path = Paths.get(destinationPath);
		Files.write(path, data);

		if (driver != null) {
			driver.quit();
		}
	}
	
	@BeforeClass
	public void beforeClass() throws Exception {
		ExcelUtil.setExcelFile("TestData.xlsx", "Amazon");
	}
	
	public AndroidDriver<AndroidElement> getDriver(){
		return driver;
	}

}
