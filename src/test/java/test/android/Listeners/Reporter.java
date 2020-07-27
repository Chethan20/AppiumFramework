package test.android.Listeners;

import java.io.IOException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import test.android.utils.DriverBase;

public class Reporter implements ISuiteListener, ITestListener{
	
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
        try {
			imagepath = DriverBase.captureScreenshot(testname + "Passed");
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
        try {
        	imagepath = DriverBase.captureScreenshot(testname + "Failed");
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

}
