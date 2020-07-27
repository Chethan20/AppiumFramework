package test.android;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import test.android.pages.LoginPage;
import test.android.pages.OrderProduct;
import test.android.utils.DriverBase;
import test.android.utils.Generic;
import test.android.utils.PropertyReader;

public class AmazonTest {
	
	AndroidDriver<AndroidElement> driver;
	AppiumDriverLocalService service;
	
	@BeforeTest
	public void beforeTest() {
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }
		
		driver = DriverBase.getDriverInstance();
		driver.startRecordingScreen(
                new AndroidStartScreenRecordingOptions());
	}
	
	@AfterTest
	public void afterTest() throws IOException {
		String base64String = driver.stopRecordingScreen();
		byte[] data = Base64.decodeBase64(base64String);
		String destinationPath = System.getProperty("user.dir") + "\\target\\video.mp4";
		System.out.println("Video path:"+destinationPath);
		Path path = Paths.get(destinationPath);
		Files.write(path, data);
		
		if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
	}
	
	
	@Test(enabled=true)
	public void login() throws InterruptedException {
		
		LoginPage login = new LoginPage(driver);
		login.signIn();
		login.inputEmail(PropertyReader.loadTestData().getProperty("username"));
		login.inputPassword(PropertyReader.loadTestData().getProperty("password"));
		boolean loginverification = login.verifyLogin();
		
		assertTrue(loginverification);
		
	}
	
	@Test
	public void orderTV() throws InterruptedException {
		Generic action = new Generic(driver);
		OrderProduct order = new OrderProduct(driver);
		
		order.searchProduct(PropertyReader.loadTestData().getProperty("productsearch"));
		for(int i=0;i<3;i++) {
			if(order.selectByBrand(PropertyReader.loadTestData().getProperty("brandname")))
				break;
			else
				action.swipeDown();
		}
		order.getProductDetails();
		
		for(int i=0;i<3;i++) {
				action.swipeDown();
		}
		order.addToCart();

		boolean verifyproduct = order.verifyProductAtCheckout();
		
		assertTrue(verifyproduct);
	}

}
