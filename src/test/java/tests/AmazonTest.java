package tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginPage;
import pages.OrderProductPage;
import utilities.PropertyReader;

/**
 * @author Chethan
 *
 */
public class AmazonTest extends BaseTest {

	@Test(enabled = true)
	public void login() throws InterruptedException {

		LoginPage login = new LoginPage(driver);
		login.signIn();
		login.inputEmail(PropertyReader.loadTestData().getProperty("username"));
		login.inputPassword(PropertyReader.loadTestData().getProperty("password"));
		boolean loginverification = login.verifyLogin();

		assertTrue(loginverification);
	}

	@Test
	public void orderTV() {
		SoftAssert verify = new SoftAssert();
		OrderProductPage order = new OrderProductPage(driver);

		order.searchProduct(PropertyReader.loadTestData().getProperty("productsearch"));
		boolean checkselected = order.selectByBrand(PropertyReader.loadTestData().getProperty("brandname"));
		verify.assertTrue(checkselected);
		order.getProductDetails();
		order.addToCart();
		boolean checkproduct = order.verifyProductAtCheckout();
		verify.assertTrue(checkproduct);

		verify.assertAll();
	}
}
