package tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginPage;
import pages.OrderProductPage;
import utilities.ExcelUtil;
import utilities.PropertyReader;

/**
 * @author Chethan
 *
 */
public class AmazonTest extends BaseTest {

	@Test(priority=1)
	public void login() throws Exception {
		LoginPage login = new LoginPage(driver);
		login.signIn();
		login.inputEmail(ExcelUtil.getCellData(0,1));
		login.inputPassword(ExcelUtil.getCellData(1,1));
		boolean loginverification = login.verifyLogin();

		assertTrue(loginverification);
	}

	@Test(priority=2)
	public void orderTV() throws Exception {
		SoftAssert verify = new SoftAssert();
		OrderProductPage order = new OrderProductPage(driver);

		order.searchProduct(ExcelUtil.getCellData(2,1));
		boolean checkselected = order.selectByBrand(ExcelUtil.getCellData(3,1));
		verify.assertTrue(checkselected);
		order.getProductDetails();
		order.addToCart();
		boolean checkproduct = order.verifyProductAtCheckout();
		verify.assertTrue(checkproduct);

		verify.assertAll();
	}
}
