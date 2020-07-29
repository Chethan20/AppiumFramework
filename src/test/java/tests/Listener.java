package tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @author Chethan
 *
 */
public class Listener implements ISuiteListener, ITestListener{
	
	AndroidDriver<AndroidElement> driver;
	static ExtentTest test;
	static ExtentReports report;
	
	public void onStart(ISuite suite) {
        report = new ExtentReports(System.getProperty("user.dir")+"\\target\\ExtentReport.html");
        
    }
    public void onFinish(ISuite suite) {
        report.flush();
    }
    
    public void onTestStart(ITestResult result) {
        test = report.startTest(result.getName());
    }
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Successfully Finished" + result.getName());
        String imagepath = null;
        String testname = result.getName();
        Object object = result.getInstance();
        driver = ((BaseTest)object).getDriver();
        try {
			imagepath = captureScreenshot(driver, testname + "Passed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        test.log(LogStatus.PASS, result.getName(), test.addScreenCapture(imagepath));
    }
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed" + result.getName());
        String imagepath = null;
        String testname = result.getName();
        Object object = result.getInstance();
        driver = ((BaseTest)object).getDriver();
        try {
        	imagepath = captureScreenshot(driver, testname + "Failed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        test.log(LogStatus.FAIL, result.getName(), test.addScreenCapture(imagepath));
    }
  
    public void onFinish(ITestContext context) {
        report.endTest(test);
    }
	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method captures the window screenshot
	 * @param driver - Current driver instance
	 * @param filename - screenshot filename 
	 * @return full image path
	 * @throws IOException
	 */
	private String captureScreenshot(AndroidDriver<AndroidElement> driver, String filename) throws IOException {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imgpath = System.getProperty("user.dir") + "\\screenshots\\" + filename + ".jpeg";
		File imgfile = new File(imgpath);
		FileUtils.copyFile(source, imgfile);
		return imgpath;

	}

}

