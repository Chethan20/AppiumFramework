package test.android.pages;

import java.util.Base64;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage {
	
	private AndroidDriver<AndroidElement> driver;
	
	public LoginPage(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(id="com.amazon.mShop.android.shopping:id/sign_in_button")
	private AndroidElement signIn_button;
	
	@AndroidFindBy(id="com.amazon.mShop.android.shopping:id/signin_to_yourAccount")
	private AndroidElement title;
	
	@AndroidFindBy(className="android.widget.EditText")
	private AndroidElement email;
	
	@AndroidFindBy(className="android.widget.Button")
	private AndroidElement continue_button;
	
	@AndroidFindBy(className="android.widget.EditText")
	private AndroidElement password;
	
	@AndroidFindBy(xpath="(//android.widget.TextView)[1]")
	private AndroidElement verify_email;
	
	@AndroidFindBy(xpath="(//android.widget.Button)[1]")
	private AndroidElement submit_button;
	
	@AndroidFindBy(xpath="(//android.view.View)[4]")
	private AndroidElement invalidpassword_err;
	
	@AndroidFindBy(accessibility="Cart")
	private AndroidElement cart;
	
	public void signIn() {
		if(signIn_button.isDisplayed())
			signIn_button.click();
		else
			throw new NoSuchElementException("Element is no longer available");
	}
	
	public void inputEmail(String username) {
		
		if(email.isDisplayed()) {
			email.click();
			email.sendKeys(username);
			if(driver.isKeyboardShown())
				driver.hideKeyboard();
			if(continue_button.isDisplayed())
				continue_button.click();
		}
		else
			throw new NoSuchElementException("Element is no longer available");
	}
	
	public void inputPassword(String encodedpassword) throws InterruptedException {
		byte[] decodepwd = Base64.getDecoder().decode(encodedpassword.getBytes());
		String pwd = new String(decodepwd);
		if (password.isDisplayed()) {
			password.click();
			password.sendKeys(pwd);
			if (driver.isKeyboardShown())
				driver.hideKeyboard();
			if (submit_button.isDisplayed()) {
				submit_button.click();
				Thread.sleep(7000);
			}
		}
	}
	
	public boolean verifyLogin() {
		return cart.isDisplayed();
	}

}
