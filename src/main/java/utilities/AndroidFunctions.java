package utilities;

import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotVisibleException;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

/**
 * @author Chethan
 *
 */
public class AndroidFunctions {

	private AndroidDriver<AndroidElement> driver;
	private AndroidTouchAction action;

	public AndroidFunctions(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		this.action = new AndroidTouchAction(driver);
	}
	
	/**
	 * Hides the keyboard from the current window
	 */
	public void closeKeyboard() {
		if (driver.isKeyboardShown())
			driver.hideKeyboard();
	}

	/**
	 * This method presses the key based on the parameter
	 * @param key Gets the keys available in the Android Keyboard
	 */
	public void keyboardAction(AndroidKey key) {
		if (!driver.isKeyboardShown()) {
			driver.getKeyboard();
			driver.pressKey(new KeyEvent(key));
		} else
			driver.pressKey(new KeyEvent(key));
	}
	
	/**
	 * This method verifies the visibility of the Android Element
	 * @param element Element to be operated 
	 * @return Boolean value based on the visibility of the element
	 */
	public boolean isElementfound(AndroidElement element) {
		boolean found;
		if(element!=null)
			found = element.isDisplayed();
		else {
			throw new NullPointerException();
		}
		return found;
	}

	/**
	 * This method passes the text value to the Android Element
	 * @param element Element to be operated
	 * @param input String value to be send to the element
	 */
	public void inputText(AndroidElement element, String input) {
		if (isElementfound(element)) {
			if (element.getText().isEmpty()) {
				element.click();
				element.sendKeys(input);
			} else {
				element.clear();
				element.click();
				element.sendKeys(input);
			}
		} else
			throw new ElementNotVisibleException("Element not displayed");
	}

	/**
	 * This method clicks on the Android Element
	 * @param element Element to be operated
	 */
	public void clickElement(AndroidElement element) {
		if (isElementfound(element)) {
			if (element.isEnabled())
				element.click();
			else
				throw new ElementClickInterceptedException("Unable to click:" + element.getText());
		} else
			throw new ElementNotVisibleException("Element not displayed");
	}

	/**
	 * This method scrolls to the target element with source element as the reference
	 * @param source Visible Android Element
	 * @param target Non-visible Android Element
	 */
	public void scrollToElement(AndroidElement source, AndroidElement target) {
		if(source!=null && target!=null) {
			action.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(source)).withDuration(Duration.ofSeconds(2)))
			.moveTo(ElementOption.element(source))
			.release()
			.perform();
		}
		else
			throw new NullPointerException();
	}
	

	/**
	 * This method taps on the element. Similar to click operation
	 * @param element Element to be operated
	 */
	public void tapOnElement(AndroidElement element) {
		if(element!=null) {
			action.tap(TapOptions.tapOptions().withElement(ElementOption.element(element)))
			.perform();
		}
		else
			throw new NullPointerException();
		
	}
	
	/**
	 * This method swipes to the left of the current window
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
	
	/**
	 * This method swipes to the right of the current window
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
	
	/**
	 * This method swipes to the bottom of the current window
	 */
	public void swipeDown() {
		Dimension size = driver.manage().window().getSize();
		
		int x = (int) (size.width * 0.5);
		int starty = (int) (size.height * 0.6);
		int endy = (int) (size.height * 0.3);
		
		action.longPress(PointOption.point(x, starty))
				.moveTo(PointOption.point(x, endy))
				.release()
				.perform();
	}
	
	/**
	 * This method swipes to the top of the current window
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
