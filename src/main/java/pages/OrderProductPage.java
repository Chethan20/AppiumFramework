package pages;

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
import utilities.AndroidFunctions;

/**
 * @author Chethan
 *
 */
public class OrderProductPage extends AndroidFunctions {

	private String tvtitle;
	private String tvprice;

	public OrderProductPage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@AndroidFindAll(value = { @AndroidBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text", priority = 1),
			@AndroidBy(uiAutomator = "new UiSelector().resourceId(\"com.amazon.mShop.android.shopping:id/rs_search_src_text\")") })
	private AndroidElement searchbox;

	@AndroidFindBy(xpath = "//*[@resource-id=\"com.amazon.mShop.android.shopping:id/rs_item_styled_byline\"]//android.widget.TextView")
	private List<AndroidElement> tvBrandSearch;

	@AndroidFindBy(xpath = "//android.view.View[@resource-id=\"title_feature_div\"]/android.view.View")
	private AndroidElement tvProduct_title;

	@AndroidFindBy(xpath = "//android.view.View[@resource-id=\"atfRedesign_priceblock_priceToPay\"]/android.widget.EditText")
	private AndroidElement tvProduct_price;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"add-to-cart-button\")")
	private AndroidElement addcart_button;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"a-autoid-0-announce\")")
	private AndroidElement cart_button;

	@AndroidFindBy(xpath = "//*[@resource-id=\"activeCartViewForm\"]//child::android.view.View[3]/android.view.View/android.widget.TextView")
	private AndroidElement checkout_tvtitle;

	@AndroidFindBy(xpath = "//*[@resource-id=\"activeCartViewForm\"]//child::android.view.View[3]/android.widget.ListView/android.view.View[1]")
	private AndroidElement checkout_tvprice;

	/**
	 * This method searches the item
	 * @param item Product to be searched
	 */
	public void searchProduct(String item) {
		inputText(searchbox, item);
		keyboardAction(AndroidKey.ENTER);
	}

	/**
	 * This method searches and selects the brand product from the search result
	 * @param brandname Brand to be searched
	 * @return verifies the search
	 */
	public boolean selectByBrand(String brandname) {

		boolean find = false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < tvBrandSearch.size(); j++) {
				if (tvBrandSearch.get(j).getText().contains(brandname)) {
					tvBrandSearch.get(j).click();
					find = true;
					break;
				}
			}
			if (find)
				break;
			else
				swipeDown();
		}
		return find;
	}

	/**
	 * This method gets the selected product title and price from the current window
	 */
	public void getProductDetails() {
		this.tvtitle = tvProduct_title.getText();
		this.tvprice = tvProduct_price.getText();
	}

	/**
	 * This method adds the product to the cart
	 */
	public void addToCart() {
		for (int i = 0; i < 3; i++) {
			swipeDown();
		}
		clickElement(addcart_button);
		clickElement(cart_button);
	}

	/**
	 * This method checks whether the product selected is same at the checkout
	 * @return checks title and price
	 */
	public boolean verifyProductAtCheckout() {
		String title;
		String price;

		title = checkout_tvtitle.getText();
		title = formatTitleAtCheckout(title);
		price = checkout_tvprice.getText();
		float checkoutprice = formatPriceAtCheckout(price);
		float productprice = formatPriceAtProductpage(tvprice);

		boolean checktitle = tvtitle.contains(title);
		boolean checkprice = (checkoutprice == productprice) ? true : false;

		return checktitle && checkprice;
	}

	/**
	 * Formats the product title at checkout window
	 * @param titleAtCheckout Title of the product
	 * @return formatted titles
	 */
	private String formatTitleAtCheckout(String titleAtCheckout) {
		String format = titleAtCheckout.replace("...", "");
		return format;

	}

	/**
	 * Formats the product price at product window
	 * @param priceAtProductpage Product price
	 * @return formatted price
	 */
	private float formatPriceAtProductpage(String priceAtProductpage) {
		char[] ch = priceAtProductpage.toCharArray();
		String st = new String();

		for (Character c : ch) {
			if (Character.isDigit(c))
				st = st + c;
		}
		float price = Float.parseFloat(st);
		return price;
	}

	/**
	 * Formats the product price at the checkout window
	 * @param priceAtCheckout Product price
	 * @return formatted price
	 */
	private float formatPriceAtCheckout(String priceAtCheckout) {

		char[] ch = priceAtCheckout.toCharArray();
		String str = new String();

		for (int i = 0; i < ch.length; i++) {
			if (Character.isDigit(ch[i])) {
				str = str + ch[i];
			} else if (ch[i] == '.') {
				if (ch[i + 1] == '0')
					break;
				else
					str = str + ch[i];
			}
		}
		float price = Float.parseFloat(str);
		return price;
	}
}
