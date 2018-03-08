package com.test.automation.uiAutomation.homepage;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.HomePage;

public class TC002_VerifyLoginWithInvalidCredentials extends TestBase {
	public static final Logger log = Logger
			.getLogger(TC002_VerifyLoginWithInvalidCredentials.class.getClass());
	HomePage homepage;
	
	@DataProvider(name="loginData")
	public String[][] getTestData(){
		String[][] testRecords = getData("Testdata.xlsx", "login");
		return testRecords;
	}

	@BeforeClass
	public void setUp() throws IOException {
		init();
	}

	@Test(dataProvider="loginData")
	public void verifyLoginWithvalidCredentials(String emailaddress,String password,String runmode) throws InterruptedException {
		if (runmode.equalsIgnoreCase("n")){
			throw new SkipException(" User marked as no run");
		}
		log("=========== Starting verifyLoginWithvalidCredentials=======");
		homepage = new HomePage(driver);
		homepage.loginToApplication(emailaddress, password);
		getScreenshot(emailaddress);
			homepage.signout();
		/*boolean status = homepage.signoutDisplayed();
		if(status){
			homepage.signout();
		}
		Assert.assertEquals(true, status);
		Assert.assertEquals(homepage.getInvalidLoginText(),
				"Authentication failed.");*/
		log("=========== Ending verifyLoginWithvalidCredentials=======");
	}

	
	public void log(String data){
		log.info(data);
		Reporter.log(data);
		
	}
}
