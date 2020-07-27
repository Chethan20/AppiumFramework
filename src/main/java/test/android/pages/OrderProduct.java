package test.android.pages;

import java.util.List;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;

public class OrderProduct {

private AndroidDriver<AndroidElement> driver;
private String tvtitle;
private String tvprice;
	
	public OrderProduct(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@HowToUseLocators(androidAutomation=LocatorGroupStrategy.ALL_POSSIBLE)
	@AndroidFindAll(value={
		@AndroidBy(id="com.amazon.mShop.android.shopping:id/rs_search_src_text", priority=1),
		@AndroidBy(uiAutomator="new UiSelector().resourceId(\"com.amazon.mShop.android.shopping:id/rs_search_src_text\")")})
	private AndroidElement searchbox;

	@AndroidFindBy(xpath="//*[@resource-id=\"com.amazon.mShop.android.shopping:id/rs_item_styled_byline\"]//android.widget.TextView")
	private List<AndroidElement> tvBrandSearch;
	
	@AndroidFindBy(xpath="//android.view.View[@resource-id=\"title_feature_div\"]/android.view.View")
	private AndroidElement tvProduct_title;
	
	@AndroidFindBy(xpath="//android.view.View[@resource-id=\"atfRedesign_priceblock_priceToPay\"]/android.widget.EditText")
	private AndroidElement tvProduct_price;
	
	@AndroidFindBy(uiAutomator="new UiSelector().resourceId(\"add-to-cart-button\")")
	private AndroidElement addcart_button;
	
	@AndroidFindBy(uiAutomator="new UiSelector().resourceId(\"a-autoid-0-announce\")")
	private AndroidElement cart_button;
	
	@AndroidFindBy(xpath="//*[@resource-id=\"activeCartViewForm\"]//child::android.view.View[3]/android.view.View/android.widget.TextView")
	private AndroidElement checkout_tvtitle;
	
	@AndroidFindBy(xpath="//*[@resource-id=\"activeCartViewForm\"]//child::android.view.View[3]/android.widget.ListView/android.view.View[1]")
	private AndroidElement checkout_tvprice;

	
	public void searchProduct(String item) {
		if(searchbox.isDisplayed()) {
			searchbox.click();
			searchbox.sendKeys(item);
			driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.ENTER));
		}
	}
	
	public boolean selectByBrand(String brandname) {
		
		boolean find = false;
		
		for(int i=0;i<tvBrandSearch.size();i++) {
			if(tvBrandSearch.get(i).getText().contains(brandname)) {
				tvBrandSearch.get(i).click();
				find = true;
				break;
			}	
		}
		return find;	
	}
	
	public void getProductDetails() {
		this.tvtitle = tvProduct_title.getText();
		this.tvprice = tvProduct_price.getText();
	}
	
	public void addToCart() {
		if(addcart_button.isEnabled())
			addcart_button.click();
		cart_button.click();
	}
	
	public boolean verifyProductAtCheckout() {
		String title;
		String price;
		
		title = checkout_tvtitle.getText();
		title = formatTitleAtCheckout(title);
		price = checkout_tvprice.getText();
		float checkoutprice = formatPriceAtCheckout(price);
		float productprice = formatPriceAtProductpage(tvprice);
		
		boolean checktitle = tvtitle.contains(title);
		boolean checkprice = (checkoutprice == productprice)?true:false;

		return checktitle && checkprice;
	}
	
	private String formatTitleAtCheckout(String titleAtCheckout) {
		String format = titleAtCheckout.replace("...", "");
		return format;
		
	}
	
	private float formatPriceAtProductpage(String priceAtProductpage) {
		char[] ch = priceAtProductpage.toCharArray();
		String st = new String();
		
		for(Character c:ch)
		{
			if(Character.isDigit(c))
				st=st+c;
		}
		float price = Float.parseFloat(st);
		return price;
	}
	
	private float formatPriceAtCheckout(String priceAtCheckout) {
		
		char[] ch = priceAtCheckout.toCharArray();
		String str = new String();
		
		for(int i=0;i<ch.length;i++)
		{
			if(Character.isDigit(ch[i]))
			{
				str=str+ch[i];	
			} else if(ch[i] == '.')
			{
				if(ch[i+1] == '0')
					break;
				else
					str=str+ch[i];
			}
		}
		float price = Float.parseFloat(str);
		return price;
	}
}
