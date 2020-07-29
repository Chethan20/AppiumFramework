package pages;

import java.util.Base64;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utilities.AndroidFunctions;

/**
 * @author Chethan
 *
 */
public class LoginPage extends AndroidFunctions {
	
	public LoginPage(AndroidDriver<AndroidElement> driver) {
		super(driver);
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
	
	/**
	 * This method takes to the Amazon sign-in window
	 */
	public void signIn() {
		clickElement(signIn_button);
	}
	
	/**
	 * This method inputs the username in the sign-in window
	 * @param username email id as input
	 */
	public void inputEmail(String username) {
		inputText(email, username);
		closeKeyboard();
		clickElement(continue_button);
	}
	
	/**
	 * This method inputs the password in the sign-in window
	 * @param encodedpassword Password encoded
	 * @throws InterruptedException
	 */
	public void inputPassword(String encodedpassword) throws InterruptedException {
		byte[] decodepwd = Base64.getDecoder().decode(encodedpassword.getBytes());
		String pwd = new String(decodepwd);
		inputText(password, pwd);
		closeKeyboard();
		clickElement(submit_button);
		Thread.sleep(6000);
	}
	
	/**
	 * @return verifies home screen is displayed 
	 */
	public boolean verifyLogin() {
		return isElementfound(cart);
	}

}
