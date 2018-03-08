package com.test.automation.uiAutomation.customListner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.test.automation.uiAutomation.testBase.TestBase;

public class Listner extends TestBase implements ITestListener {
	
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String methodName = result.getName();
		log("test failure");
		if (!result.isSuccess()) {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			try {
				String reportDirectory = new File(
						System.getProperty("user.dir")).getAbsolutePath()
						+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\screenshot\\";

				File destFile = new File((String) reportDirectory
						+ "\\failure_screenshots\\" + methodName + "_"
						+ formatter.format(calendar.getTime()) + ".png");
				FileUtils.copyFile(scrFile, destFile);
				Reporter.log("<a href='" + destFile.getAbsolutePath()
						+ "'> <img src='" + destFile.getAbsolutePath()
						+ "' height='100' width='100'/> </a>");
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public void onTestSkipped(ITestResult result) {
		Reporter.log(" Test skipped running :"+result.getMethod().getMethodName());
		
	}

	public void onTestStart(ITestResult result) {
		Reporter.log(" Test Started running :"+result.getMethod().getMethodName());
		
	}

	public void onTestSuccess(ITestResult result) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String methodName = result.getName();
		log("test success");
		if (result.isSuccess()) {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			try {
				String reportDirectory = new File(
						System.getProperty("user.dir")).getAbsolutePath()
						+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\screenshot\\";

				File destFile = new File((String) reportDirectory
						+ "\\success_screenshots\\" + methodName + "_"
						+ formatter.format(calendar.getTime()) + ".png");
				FileUtils.copyFile(scrFile, destFile);
				Reporter.log("<a href='" + destFile.getAbsolutePath()
						+ "'> <img src='" + destFile.getAbsolutePath()
						+ "' height='100' width='100'/> </a>");
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		
	}

}
