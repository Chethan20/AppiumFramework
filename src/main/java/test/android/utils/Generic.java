package test.android.utils;

import java.time.Duration;

import org.openqa.selenium.Dimension;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class Generic {

	private AndroidDriver<AndroidElement> driver;
	private AndroidTouchAction action;

	public Generic(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		this.action = new AndroidTouchAction(driver);
	}

	/*
	 * Scrolls to the Element from the Base Element position
	 */
	public void scrollToElement(AndroidElement source, AndroidElement target) {
		action.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(source)).withDuration(Duration.ofSeconds(2)))
				.moveTo(ElementOption.element(source))
				.release()
				.perform();

	}
	
	/*
	 * Taps on the Element. Similar to click
	 */
	public void tapOnElement(MobileElement element) {
		action.tap(TapOptions.tapOptions().withElement(ElementOption.element(element)))
				.perform();
		
	}
	
	/*
	 * Swipes to the Left of the Screen
	 */
	public void swipeLeft() {
		
		Dimension size = driver.manage().window().getSize();
		int y = (int) (size.height * 0.5);
		int startx = (int) (size.width * 0.3);
		int endx = (int) (size.width * 0.6);
		
		action.longPress(PointOption.point(startx, y))
				.moveTo(PointOption.point(endx, y))
				.release()
				.perform();
	}
	
	/*
	 * Swipes to the Right of the Screen
	 */
	public void swipeRight() {
			
			Dimension size = driver.manage().window().getSize();
			int y = (int) (size.height * 0.5);
			int startx = (int) (size.width * 0.6);
			int endx = (int) (size.width * 0.3);
			
			action.longPress(PointOption.point(startx, y))
					.moveTo(PointOption.point(endx, y))
					.release()
					.perform();
	}
	
	/*
	 * Swipes Down the Screen
	 */
	public void swipeDown() throws InterruptedException {
		//Thread.sleep(2000);
		Dimension size = driver.manage().window().getSize();
		
		int x = (int) (size.width * 0.5);
		int starty = (int) (size.height * 0.6);
		int endy = (int) (size.height * 0.3);
		
		action.longPress(PointOption.point(x, starty))
				.moveTo(PointOption.point(x, endy))
				.release()
				.perform();
	}
	
	
	/*
	 * Swipes Up the Screen
	 */
	public void swipeUp() {
		
		Dimension size = driver.manage().window().getSize();
		int x = (int) (size.width * 0.5);
		int starty = (int) (size.height * 0.3);
		int endy = (int) (size.height * 0.6);
		
		action.longPress(PointOption.point(x, starty))
				.moveTo(PointOption.point(x, endy))
				.release()
				.perform();
	}

}
